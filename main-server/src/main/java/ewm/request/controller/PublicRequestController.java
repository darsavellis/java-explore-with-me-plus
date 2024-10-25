package ewm.request.controller;

import ewm.request.dto.ParticipationRequestDto;
import ewm.request.service.PublicRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicRequestController {
    final PublicRequestService requestService;

    @GetMapping("/users/{userId}/requests")
    List<ParticipationRequestDto> getSentBy(@PathVariable long userId) {
        return requestService.getSentBy(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests")
    ParticipationRequestDto send(@PathVariable long userId, @RequestParam Long eventId) {
        return requestService.send(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        return requestService.cancel(requestId, userId);
    }
}
