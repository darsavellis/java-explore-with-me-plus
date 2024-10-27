package ewm.event.service.impl;


import com.querydsl.core.BooleanBuilder;
import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.service.PublicCategoryService;
import ewm.event.dto.AdminEventParam;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.UpdateEventAdminRequest;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.model.EventState;
import ewm.event.model.QEvent;
import ewm.event.repository.EventRepository;
import ewm.event.service.AdminEventService;
import ewm.exception.ConflictException;
import ewm.exception.NotFoundException;
import ewm.request.model.RequestStatus;
import ewm.request.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    final EventRepository eventRepository;
    final RequestRepository requestRepository;
    final PublicCategoryService categoryService;
    final EventMapper eventMapper;
    final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public List<EventFullDto> getAllBy(AdminEventParam eventParam) {
        Pageable pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize());
        QEvent qEvent = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        Optional.ofNullable(eventParam.getUsers())
            .ifPresent(userIds -> booleanBuilder.and(qEvent.initiator.id.in(userIds)));
        Optional.ofNullable(eventParam.getStates())
            .ifPresent(userStates -> booleanBuilder.and(qEvent.state.in(userStates)));
        Optional.ofNullable(eventParam.getCategories())
            .ifPresent(categoryIds -> booleanBuilder.and(qEvent.category.id.in(categoryIds)));
        Optional.ofNullable(eventParam.getRangeStart())
            .ifPresent(rangeStart -> booleanBuilder.and(qEvent.eventDate.after(rangeStart)));
        Optional.ofNullable(eventParam.getRangeEnd())
            .ifPresent(rangeEnd -> booleanBuilder.and(qEvent.eventDate.before(rangeEnd)));

        List<Event> events = eventRepository.findAll(booleanBuilder, pageable).getContent();

        Map<Long, Long> requestCountMap = requestRepository.findAllByEventIdInAndStatusIs(
            events.stream().map(Event::getId).toList(), RequestStatus.CONFIRMED
        ).stream().collect(Collectors.groupingBy(req -> req.getEvent().getId(), Collectors.counting()));

        events.forEach(event -> {
            event.setConfirmedRequests(requestCountMap.getOrDefault(event.getId(), 0L));
        });

        return events.stream().map(eventMapper::toEventFullDto).toList();
    }

    @Override
    @Transactional
    public EventFullDto updateBy(long eventId, UpdateEventAdminRequest updateEventUserRequest) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException("Событие с с id = " + eventId + " не найдено"));

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие" + event.getId() + "уже опубликовано");
        }

        if (event.getState().equals(EventState.CANCELED)) {
            throw new ConflictException("Нельзя опубликовать отмененное событие");
        }

        Category category = event.getCategory();
        eventRepository.save(eventMapper.toUpdatedEvent(event, updateEventUserRequest, category));
        return eventMapper.toEventFullDto(event);
    }
}
