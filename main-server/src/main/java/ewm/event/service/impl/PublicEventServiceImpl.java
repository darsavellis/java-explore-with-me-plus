package ewm.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import ewm.client.StatRestClientImpl;
import ewm.dto.ViewStatsDto;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.mappers.EventMapper;
import ewm.event.model.EventState;
import ewm.event.model.QEvent;
import ewm.event.repository.EventRepository;
import ewm.event.service.PublicEventService;
import ewm.exception.NotFoundException;
import ewm.request.model.RequestStatus;
import ewm.request.repository.ConfirmedRequests;
import ewm.request.repository.RequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicEventServiceImpl implements PublicEventService {
    final EventRepository eventRepository;
    final RequestRepository requestRepository;
    final StatRestClientImpl statRestClient;
    final EventMapper eventMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllBy(PublicEventParam eventParam) {
        QEvent qEvent = QEvent.event;
        PageRequest pageRequest = PageRequest.of(eventParam.getFrom(), eventParam.getSize());

        BooleanBuilder booleanBuilder = buildExpression(eventParam, qEvent);

        List<EventShortDto> events = eventRepository.findAll(booleanBuilder, pageRequest)
            .stream().map(eventMapper::toEventShortDto).toList();

        List<Long> eventIds = events.stream().map(EventShortDto::getId).toList();
        RequestStatus status = RequestStatus.CONFIRMED;
        Map<Long, Long> confirmedRequestsMap = requestRepository
            .findAllByEventIdInAndStatusIs(eventIds, status)
            .stream().collect(Collectors.toMap(ConfirmedRequests::getEventId, ConfirmedRequests::getConfirmedRequests));

        Set<String> uris = events.stream()
            .map(event -> "/events/" + event.getId()).collect(Collectors.toSet());

        LocalDateTime start = events
            .stream()
            .min(Comparator.comparing(EventShortDto::getEventDate))
            .orElseThrow()
            .getEventDate();

        Map<String, Long> viewMap = statRestClient
            .stats(start, LocalDateTime.now(), uris.stream().toList(), false).stream()
            .collect(Collectors.groupingBy(ViewStatsDto::getUri, Collectors.summingLong(ViewStatsDto::getHits)));

        return events.stream().peek(shortDto -> {
            shortDto.setViews(viewMap.getOrDefault("/events/" + shortDto.getId(), 0L));
            shortDto.setConfirmedRequests(confirmedRequestsMap.getOrDefault(shortDto.getId(), 0L));
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getBy(long eventId) {
        EventFullDto event = eventRepository.findById(eventId).map(eventMapper::toEventFullDto)
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
        return event;
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
}
