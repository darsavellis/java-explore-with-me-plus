package ewm.event.service.impl;

import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.service.PublicCategoryService;
import ewm.client.StatRestClients;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.event.service.PrivateEventService;
import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import ewm.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateEventServiceImpl implements PrivateEventService {
    final UserService userService;
    final PublicCategoryService categoryService;
    final StatRestClients statRestClients;
    final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getAllBy(long userId, int from, int size) {
        return List.of();
    }

    @Transactional
    @Override
    public EventFullDto create(long userId, NewEventDto newEventDto) {
        User initiator = UserMapper.toUser(userService.getBy(userId));
        Category category = CategoryMapper.toCategory(categoryService.getBy(newEventDto.getCategory()));
        Event event = EventMapper.toEvent(newEventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto getBy(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateBy(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        return List.of();
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(long userId, long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }
}
