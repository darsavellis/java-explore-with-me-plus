package ewm.subscriptions.controller;

import ewm.subscriptions.dto.SubscriptionDto;
import ewm.subscriptions.service.SubscriptionService;
import ewm.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SubscriptionController {
    final SubscriptionService subscriptionService;

    @GetMapping("/users/{userId}/subscriptions/following")
    Set<UserShortDto> findFollowingBy(@PathVariable long userId,
                                      @RequestParam(required = false, defaultValue = "0") int from,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return subscriptionService.findFollowing(userId, PageRequest.of(from, size));
    }

    @GetMapping("/users/{userId}/subscriptions/followers")
    Set<UserShortDto> findFollowersBy(@PathVariable long userId,
                                      @RequestParam(required = false, defaultValue = "0") int from,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return subscriptionService.findFollowers(userId, PageRequest.of(from, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/subscriptions/{followingId}")
    SubscriptionDto follow(@PathVariable long userId, @PathVariable long followingId) {
        return subscriptionService.follow(userId, followingId);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{followingId}")
    void unfollow(@PathVariable long userId, @PathVariable long followingId) {
        subscriptionService.unfollow(userId, followingId);
    }
}
