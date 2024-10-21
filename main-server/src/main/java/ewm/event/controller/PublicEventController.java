package ewm.event.controller;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.PublicEventParam;
import ewm.event.service.PublicEventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    final PublicEventService publicEventService;

    @GetMapping
    List<EventShortDto> getAllBy(@ModelAttribute PublicEventParam publicEventParam,
                                 HttpServletRequest request) {
        return publicEventService.getAllBy(publicEventParam, request);
    }

    @GetMapping("/{eventId}")
    EventFullDto getBy(@PathVariable long eventId, HttpServletRequest request) {
        return publicEventService.getBy(eventId, request);
    }
}
