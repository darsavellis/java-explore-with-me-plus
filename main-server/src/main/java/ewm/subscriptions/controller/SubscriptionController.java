package ewm.subscriptions.controller;

import ewm.subscriptions.dto.SubscriptionDto;
import ewm.subscriptions.service.SubscriptionService;
import ewm.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SubscriptionController {
    final SubscriptionService subscriptionService;

    @GetMapping("/admin/{userId}/subscriptions")
    Set<SubscriptionDto> findAllBy(@PathVariable long userId) {
        return subscriptionService.findAllBy(userId);
    }

    @GetMapping("/users/{userId}/subscriptions/following")
    Set<UserShortDto> findFollowingBy(@PathVariable long userId) {
        return subscriptionService.findFollowing(userId);
    }

    @GetMapping("/users/{userId}/subscriptions/followers")
    Set<UserShortDto> findFollowersBy(@PathVariable long userId) {
        return subscriptionService.findFollowers(userId);
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
