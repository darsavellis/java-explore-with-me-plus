package ewm.event.service.impl;

import ewm.event.dto.AdminEventParam;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.UpdateEventAdminRequest;
import ewm.event.service.AdminEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEventServiceImpl implements AdminEventService {
    @Override
    public List<EventFullDto> getAllBy(AdminEventParam eventParam) {
        return List.of();
    }

    @Override
    public EventFullDto updateBy(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
