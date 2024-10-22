package ewm.request.controller;

import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import ewm.request.model.ParticipationRequest;
import ewm.request.service.RequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestController {
    final RequestService requestService;

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getReceivedBy(@PathVariable long userId, @PathVariable long eventId) {
        return requestService.getReceivedBy(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult update(@PathVariable long userId, @PathVariable long eventId,
                                          @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return requestService.update(userId, eventId, updateRequest);
    }

    @GetMapping("/users/{userId}/requests")
    List<ParticipationRequestDto> getSentBy(@PathVariable long userId) {
        return requestService.getSentBy(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests")
    ParticipationRequestDto send(@PathVariable long userId, @RequestParam long eventId) {
        return requestService.send(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        return requestService.cancel(requestId, userId);
    }
}
