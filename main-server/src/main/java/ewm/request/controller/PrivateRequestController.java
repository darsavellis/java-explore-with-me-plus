package ewm.request.controller;

import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import ewm.request.service.PrivateRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateRequestController {
    final PrivateRequestService requestService;


    @GetMapping("/users/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getReceivedBy(@PathVariable long userId, @PathVariable long eventId) {
        return requestService.getReceivedBy(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult update(@PathVariable long userId, @PathVariable long eventId,
                                          @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return requestService.update(userId, eventId, updateRequest);
    }

}