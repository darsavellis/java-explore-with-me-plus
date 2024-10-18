package ewm.event.service.impl;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.service.PublicEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicEventServiceImpl implements PublicEventService {
    @Override
    public List<EventShortDto> getAllBy(PublicEventParam publicEventParam) {
        return List.of();
    }

    @Override
    public EventFullDto getBy(long eventId) {
        return null;
    }
}
