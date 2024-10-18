package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    List<EventShortDto> getAllBy(long userId, int from, int size);

    EventFullDto create(long userId, NewEventDto newEventDto);

    EventFullDto getBy(long userId, long eventId);

    EventFullDto updateBy(long userId, long eventId);

    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateRequests(long userId, long eventId,
                                                  EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
