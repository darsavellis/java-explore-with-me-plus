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
import ewm.event.model.QEvent;
import ewm.event.repository.EventRepository;
import ewm.event.service.AdminEventService;
import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    final EventRepository eventRepository;
    final PublicCategoryService categoryService;
    final EventMapper eventMapper;
    final CategoryMapper categoryMapper;

    @Override
    public List<EventFullDto> getAllBy(AdminEventParam eventParam) {
        Pageable pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize());
        QEvent event = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        Optional.ofNullable(eventParam.getUsers())
                .ifPresent(userIds -> booleanBuilder.and(event.initiator.id.in(userIds)));
        Optional.ofNullable(eventParam.getStates())
                .ifPresent(userStates -> booleanBuilder.and(event.state.in(userStates)));
        Optional.ofNullable(eventParam.getCategories())
                .ifPresent(categoryIds -> booleanBuilder.and(event.category.id.in(categoryIds)));
        Optional.ofNullable(eventParam.getRangeStart())
                .ifPresent(rangeStart -> booleanBuilder.and(event.eventDate.after(rangeStart)));
        Optional.ofNullable(eventParam.getRangeEnd())
                .ifPresent(rangeEnd -> booleanBuilder.and(event.eventDate.before(rangeEnd)));

        return eventRepository.findAll(booleanBuilder, pageable).map(eventMapper::toEventFullDto).toList();
    }

    @Override
    public EventFullDto updateBy(long eventId, UpdateEventAdminRequest updateEventUserRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с с id = " + eventId + " не найдено"));
        Category category = categoryMapper.toCategory(categoryService.getBy(event.getCategory().getId()));
        eventRepository.save(eventMapper.toUpdatedEvent(event, updateEventUserRequest, category));
        return eventMapper.toEventFullDto(event);
    }
}
