package ewm.event.controller;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.dto.UpdateEventUserRequest;
import ewm.event.service.PrivateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PrivateEventController {
    final PrivateEventService privateEventService;

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getAllBy(@PathVariable long userId, @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "1000") int size) {
        return privateEventService.getAllBy(userId, PageRequest.of(from, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Validated @RequestBody NewEventDto newEventDto) {
        return privateEventService.create(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getBy(@PathVariable long userId, @PathVariable long eventId) {
        return privateEventService.getBy(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateBy(@PathVariable long userId, @PathVariable long eventId,
                                 @Validated @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return privateEventService.updateBy(userId, eventId, updateEventUserRequest);
    }
}
