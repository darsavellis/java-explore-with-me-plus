package ewm.event.service.impl;

import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.service.PublicCategoryService;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.dto.UpdateEventUserRequest;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.model.EventState;
import ewm.event.repository.EventRepository;
import ewm.event.service.PrivateEventService;
import ewm.exception.ConflictException;
import ewm.exception.NotFoundException;
import ewm.exception.PermissionException;
import ewm.exception.ValidationException;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import ewm.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateEventServiceImpl implements PrivateEventService {
    final UserService userService;
    final PublicCategoryService categoryService;
    final EventRepository eventRepository;
    final UserMapper userMapper;
    final CategoryMapper categoryMapper;
    final EventMapper eventMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllBy(long userId, PageRequest page) {
        return eventRepository.findAllByInitiatorId(userId, page)
            .map(eventMapper::toEventShortDto).getContent();
    }

    @Override
    @Transactional
    public EventFullDto create(long userId, NewEventDto newEventDto) {
        User initiator = userMapper.toUser(userService.getBy(userId));
        Category category = categoryMapper.toCategory(categoryService.getBy(newEventDto.getCategory()));
        Event event = eventMapper.toEvent(newEventDto, initiator, category);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getBy(long userId, long eventId) {
        EventFullDto eventFullDto = eventRepository.findById(eventId).map(eventMapper::toEventFullDto)
            .orElseThrow(() -> new NotFoundException("Событие не найдено"));
        if (eventFullDto.getInitiator().getId() != userId) {
            throw new PermissionException("Доступ запрещен");
        }
        return eventFullDto;
    }

    @Override
    @Transactional
    public EventFullDto updateBy(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException("Событие с с id = " + eventId + " не найдено"));
        if (event.getInitiator().getId() != userId) {
            throw new PermissionException("Доступ запрещен");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя отменить событие с состоянием");
        }
        Category category = categoryMapper.toCategory(categoryService.getBy(event.getCategory().getId()));
        return eventMapper.toEventFullDto(eventMapper.toUpdatedEvent(event, updateEventUserRequest, category));
    }
}
