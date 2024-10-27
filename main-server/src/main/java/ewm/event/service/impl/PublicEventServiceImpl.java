package ewm.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import ewm.client.StatRestClientImpl;
import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.model.EventState;
import ewm.event.model.QEvent;
import ewm.event.repository.EventRepository;
import ewm.event.service.PublicEventService;
import ewm.exception.NotFoundException;
import ewm.request.model.RequestStatus;
import ewm.request.repository.RequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final StatRestClientImpl statRestClient;
    private final EventMapper eventMapper;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public List<EventShortDto> getAllBy(PublicEventParam eventParam, HttpServletRequest request) {
        QEvent qEvent = QEvent.event;
        QSort qSort = new QSort(QEvent.event.views.asc());
        Pageable pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize(), qSort);

        BooleanBuilder booleanBuilder = buildExpression(eventParam, qEvent);

        List<Event> events = eventRepository.findAll(booleanBuilder, pageable).stream().toList();

        Map<Long, Long> requestCountMap = requestRepository.findAllByEventIdInAndStatusIs(
            events.stream().map(Event::getId).toList(), RequestStatus.CONFIRMED
        ).stream().collect(Collectors.groupingBy(req -> req.getEvent().getId(), Collectors.counting()));

        Set<String> uris = events.stream()
            .map(event -> "/events/" + event.getId()).collect(Collectors.toSet());

        LocalDateTime start = events
            .stream()
            .min(Comparator.comparing(Event::getEventDate))
            .orElseThrow()
            .getEventDate();

        Map<String, Long> viewMap = statRestClient
            .stats(start, LocalDateTime.now(), uris.stream().toList(), false).stream()
            .collect(Collectors.groupingBy(ViewStatsDto::getUri, Collectors.summingLong(ViewStatsDto::getHits)));

        addHit("/events", request.getRemoteAddr());

        events.forEach(shortDto -> {
            shortDto.setViews(viewMap.getOrDefault("/events/" + shortDto.getId(), 0L));
            shortDto.setConfirmedRequests(requestCountMap.getOrDefault(shortDto.getId(), 0L));
        });

        if (eventParam.getSort() != null && eventParam.getSort().equalsIgnoreCase("viewMap")) {
            pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize(),
                new QSort(qEvent.views.asc()));
        }

        return eventRepository.findAll(booleanBuilder, pageable).map(eventMapper::toEventShortDto).toList();
    }

    private BooleanBuilder buildExpression(PublicEventParam eventParam, QEvent qEvent) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qEvent.state.eq(EventState.PUBLISHED));
        Optional.ofNullable(eventParam.getRangeStart())
            .ifPresent(rangeStart -> booleanBuilder.and(qEvent.eventDate.after(rangeStart)));
        Optional.ofNullable(eventParam.getRangeEnd())
            .ifPresent(rangeEnd -> booleanBuilder.and(qEvent.eventDate.before(eventParam.getRangeEnd())));
        Optional.ofNullable(eventParam.getPaid())
            .ifPresent(paid -> booleanBuilder.and(qEvent.paid.eq(paid)));
        Optional.ofNullable(eventParam.getCategories())
            .filter(category -> !category.isEmpty())
            .ifPresent(category -> booleanBuilder.and(qEvent.category.id.in(category)));
        Optional.ofNullable(eventParam.getText())
            .filter(text -> !text.isEmpty()).ifPresent(text -> {
                booleanBuilder.and(qEvent.annotation.containsIgnoreCase(text));
                booleanBuilder.or(qEvent.description.containsIgnoreCase(text));
            });
        return booleanBuilder;
    }

    @Override
    @Transactional
    public EventFullDto getBy(long eventId, HttpServletRequest request) throws InterruptedException {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException("Мероприятие с Id =" + eventId + " не найдено"));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Событие id = " + eventId + " не опубликовано");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusYears(10);

        statRestClient.stats(start, now, List.of("/events/" + eventId), true)
            .forEach(viewStatsDto -> event.setViews(viewStatsDto.getHits()));

        long confirmedRequests = requestRepository.countAllByEventIdAndStatusIs(eventId, RequestStatus.CONFIRMED);
        event.setConfirmedRequests(confirmedRequests);

        eventRepository.save(event);

        addHit("/events/" + eventId, request.getRemoteAddr());
        return eventMapper.toEventFullDto(event);
    }

    @Transactional
    private void addHit(String uri, String ip) {
        LocalDateTime now = LocalDateTime.now();
        EndpointHitDto hitDto = new EndpointHitDto("main-server", uri, ip, now.format(dateTimeFormatter));
        statRestClient.addHit(hitDto);
    }
}
