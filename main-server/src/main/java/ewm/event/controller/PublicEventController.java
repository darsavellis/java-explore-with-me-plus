package ewm.event.controller;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.service.PublicEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    final PublicEventService publicEventService;

    @GetMapping
    List<EventShortDto> getAllBy(@ModelAttribute PublicEventParam publicEventParam) {
        return publicEventService.getAllBy(publicEventParam);
    }

    @GetMapping("/{eventId}")
    EventFullDto getBy(@RequestParam long eventId) {
        return publicEventService.getBy(eventId);
    }
}
