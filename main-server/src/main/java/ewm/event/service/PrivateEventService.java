package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.dto.UpdateEventUserRequest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PrivateEventService {
    List<EventShortDto> getAllBy(long userId, PageRequest page);

    EventFullDto create(long userId, NewEventDto newEventDto);

    EventFullDto getBy(long userId, long eventId);

    EventFullDto updateBy(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);
}