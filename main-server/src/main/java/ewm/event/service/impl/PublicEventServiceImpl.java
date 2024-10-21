package ewm.event.service.impl;

import ewm.client.StatRestClient;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.service.PublicEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    final StatRestClient statRestClient;

    @Override
    public List<EventShortDto> getAllBy(PublicEventParam publicEventParam) {
        statRestClient.addHit(null);
        return List.of();
    }

    @Override
    public EventFullDto getBy(long eventId) {
        return null;
    }
}
