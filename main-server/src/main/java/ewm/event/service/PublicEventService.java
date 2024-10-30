package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;

import java.util.List;

public interface PublicEventService {
    List<EventShortDto> getAllBy(PublicEventParam publicEventParam);

    EventFullDto getBy(long eventId);

}
