package ewm.event.controller;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.EventShortDto;
import ewm.event.dto.NewEventDto;
import ewm.event.service.PrivateEventService;
import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
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
    public List<EventShortDto> getAllBy(@PathVariable long userId, @RequestParam int from, @RequestParam int size) {
        return privateEventService.getAllBy(userId, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Validated @RequestBody NewEventDto newEventDto) {
        return privateEventService.create(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getBy(@PathVariable long userId, @RequestParam long eventId) {
        return privateEventService.getBy(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateBy(@PathVariable long userId, @RequestBody long eventId) {
        return privateEventService.updateBy(userId, eventId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId, @RequestParam long eventId) {
        return privateEventService.getRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@PathVariable long userId, @RequestParam long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest
                                                             eventRequestStatusUpdateRequest) {
        return privateEventService.updateRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
