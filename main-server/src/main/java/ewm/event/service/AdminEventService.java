package ewm.event.service;

import ewm.event.dto.AdminEventParam;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventService {
    List<EventFullDto> getAllBy(AdminEventParam eventParam);

    EventFullDto updateBy(long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
