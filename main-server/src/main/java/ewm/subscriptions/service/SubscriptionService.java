package ewm.subscriptions.service;

import ewm.subscriptions.dto.SubscriptionDto;
import ewm.user.dto.UserShortDto;

import java.util.Set;

public interface SubscriptionService {
    Set<SubscriptionDto> findAllBy(long userId);

    Set<UserShortDto> findFollowing(long userId);

    Set<UserShortDto> findFollowers(long userId);

    SubscriptionDto follow(long userId, long followingId);

    void unfollow(long userId, long followingId);
}
