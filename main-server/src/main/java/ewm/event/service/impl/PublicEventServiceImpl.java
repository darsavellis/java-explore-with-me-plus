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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final StatRestClientImpl statRestClient;

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getAllBy(PublicEventParam publicEventParam, HttpServletRequest request) {

        if (publicEventParam.getFrom() == 0)
            publicEventParam.setFrom(0);

        if (publicEventParam.getSize() == 0)
            publicEventParam.setSize(10);

        Pageable pageable = PageRequest.of(publicEventParam.getFrom(),
                publicEventParam.getSize());

        if (publicEventParam.getSort() != null) {
            switch (publicEventParam.getSort()) {
                case "EVENT_DATE":
                    pageable = PageRequest.of(publicEventParam.getFrom(),
                            publicEventParam.getSize(), new QSort(QEvent.event.eventDate.asc()));
                    break;
                case "VIEWS":
                    //TODO: Сделать сортировку по количеству просмотров
                    break;
                default:
                    throw new NotFoundException("Метод сортировки не найден");
            }
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(QEvent.event.state.eq(EventState.PUBLISHED.toString()));

        if (publicEventParam.getText() != null && !publicEventParam.getText().isEmpty()) {
            booleanBuilder.and(QEvent.event.annotation.containsIgnoreCase(publicEventParam.getText()));
            booleanBuilder.or(QEvent.event.description.containsIgnoreCase(publicEventParam.getText()));
        }

        if (publicEventParam.getCategories() != null && !publicEventParam.getCategories().isEmpty()) {
            booleanBuilder.and(QEvent.event.category.id.in(publicEventParam.getCategories()));
        }

        booleanBuilder.and(QEvent.event.paid.eq(publicEventParam.isPaid()));


        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;

        if (publicEventParam.getRangeStart() != null && publicEventParam.getRangeEnd() != null) {

            rangeStart = LocalDateTime.parse(publicEventParam.getRangeStart(), dateTimeFormatter);
            rangeEnd = LocalDateTime.parse(publicEventParam.getRangeEnd(), dateTimeFormatter);

            booleanBuilder.and(QEvent.event.eventDate
                    .after(rangeStart));
            booleanBuilder.and(QEvent.event.eventDate
                    .before(rangeEnd));
        } else {
            rangeStart = LocalDateTime.MIN;
            rangeEnd = LocalDateTime.MAX;
        }

        Page<Event> eventsPage = eventRepository.findAll(booleanBuilder, pageable);

        Map<String, Long> urisAndViews = new HashMap<>();
        List<Event> events = eventsPage.stream().toList();

        for (Event event : events) {
            urisAndViews.put("/events/" + event.getId(), null);
        }

        List<ViewStatsDto> stats = statRestClient.stats(rangeStart, rangeEnd,
                urisAndViews.keySet().stream().toList(), false);

        for (ViewStatsDto stat : stats) {
            urisAndViews.put(stat.getUri(), stat.getHits());
        }

        addHit("main-server", "/events", request.getRemoteAddr());

        return eventsPage.stream().map(EventMapper::toEventShortDto)
                .peek(eventShortDto -> eventShortDto.setViews(urisAndViews.get("/events/" + eventShortDto.getId())))
                .toList();

    }

    @Override
    public EventFullDto getBy(long eventId, HttpServletRequest request) {
        addHit("main-server", "/events/" + eventId, request.getRemoteAddr());
        return eventRepository.findById(eventId)
                .map(EventMapper::toEventFullDto)
                .orElseThrow(() -> new NotFoundException("Мероприятие с Id =" + eventId + " не найдено"));
    }

    private void addHit(String application, String uri, String ip) {
        LocalDateTime now = LocalDateTime.now();
        EndpointHitDto hitDto = new EndpointHitDto(application,
                uri, ip, now.format(dateTimeFormatter));
        statRestClient.addHit(hitDto);

    }

}
