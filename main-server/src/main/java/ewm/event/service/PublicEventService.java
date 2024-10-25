package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PublicEventService {
    List<EventShortDto> getAllBy(PublicEventParam publicEventParam, HttpServletRequest request);

    EventFullDto getBy(long eventId, HttpServletRequest request) throws InterruptedException;

}
