package ewm.event.service.impl;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.service.PrivateEventService;
import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateEventServiceImpl implements PrivateEventService {

    @Override
    public List<EventShortDto> getAllBy(long userId, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto create(long userId, NewEventDto newEventDto) {

        return null;
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
