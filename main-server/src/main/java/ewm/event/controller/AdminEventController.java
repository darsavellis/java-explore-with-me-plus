package ewm.event.controller;

import ewm.event.dto.AdminEventParam;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.UpdateEventAdminRequest;
import ewm.event.service.AdminEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    final AdminEventService adminEventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)

    public List<EventFullDto> getAllBy(@Validated @ModelAttribute AdminEventParam adminEventParam) {
        return adminEventService.getAllBy(adminEventParam);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateBy(@PathVariable("eventId") long eventId,
                                 @Validated @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return adminEventService.updateBy(eventId, updateEventAdminRequest);
    }
}
