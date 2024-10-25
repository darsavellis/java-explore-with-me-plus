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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllBy(PublicEventParam eventParam, HttpServletRequest request) {
        QEvent qEvent = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Pageable pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize(),
            new QSort(qEvent.eventDate.asc()));
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

        Optional.ofNullable(eventParam.getSort())
            .filter(s -> !s.equalsIgnoreCase("EVENT_DATE"))
            .ifPresent(s -> {
                //TODO: Сделать сортировку по количеству просмотров
            });

        Map<String, Long> urisAndViews = new HashMap<>();
        List<Event> eventsPage = eventRepository.findAll(booleanBuilder, pageable)
            .stream().peek(event -> urisAndViews.put("/events/" + event.getId(), null)).toList();
        statRestClient
            .stats(eventParam.getRangeStart(), eventParam.getRangeEnd(), urisAndViews.keySet().stream().toList(), false)
            .forEach(stat -> urisAndViews.put(stat.getUri(), stat.getHits()));
        addHit("/events", request.getRemoteAddr());

        return eventsPage.stream()
            .map(eventMapper::toEventShortDto)
            .peek(eventShortDto -> eventShortDto.setViews(urisAndViews.get("/events/" + eventShortDto.getId())))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getBy(long eventId, HttpServletRequest request) throws InterruptedException {
        addHit("/events/" + eventId, request.getRemoteAddr());
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException("Мероприятие с Id =" + eventId + " не найдено"));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Событие не опубликовано");
        }

        List<ViewStatsDto> viewStatsDtos = statRestClient
            .stats(event.getCreatedOn(), LocalDateTime.now(), List.of("/events/" + eventId), true);
        long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
        event.setConfirmedRequests(confirmedRequests);

        viewStatsDtos.forEach(viewStatsDto -> event.setViews(viewStatsDto.getHits()));

        return eventMapper.toEventFullDto(event);
    }

    @Transactional
    private void addHit(String uri, String ip) {
        LocalDateTime now = LocalDateTime.now();
        EndpointHitDto hitDto = new EndpointHitDto("main-server", uri, ip, now.format(dateTimeFormatter));
        statRestClient.addHit(hitDto);
    }
}
