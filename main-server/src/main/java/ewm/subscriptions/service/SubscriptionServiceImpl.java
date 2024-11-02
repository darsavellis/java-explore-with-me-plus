package ewm.subscriptions.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ewm.exception.NotFoundException;
import ewm.subscriptions.dto.SubscriptionDto;
import ewm.subscriptions.mappers.SubscriptionMapper;
import ewm.subscriptions.model.QSubscription;
import ewm.subscriptions.model.Subscription;
import ewm.subscriptions.repository.SubscriptionRepository;
import ewm.user.dto.UserShortDto;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import ewm.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionServiceImpl implements SubscriptionService {
    final SubscriptionMapper subscriptionMapper;
    final UserMapper userMapper;
    final SubscriptionRepository subscriptionRepository;
    final UserRepository userRepository;
    final JPAQueryFactory jpaQueryFactory;
    final QSubscription qSubscription = QSubscription.subscription;

    @Override
    @Transactional(readOnly = true)
    public Set<SubscriptionDto> findAllBy(long userId) {
        return jpaQueryFactory
            .selectFrom(qSubscription)
            .from(qSubscription)
            .leftJoin(qSubscription.follower).fetchJoin()
            .leftJoin(qSubscription.following).fetchJoin()
            .stream()
            .map(subscriptionMapper::toSubscriptionShortDto)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<UserShortDto> findFollowing(long userId) {
        return jpaQueryFactory
            .selectFrom(qSubscription)
            .leftJoin(qSubscription.following).fetchJoin()
            .leftJoin(qSubscription.follower).fetchJoin()
            .where(qSubscription.follower.id.eq(userId))
            .stream()
            .map(Subscription::getFollowing)
            .map(userMapper::toUserShortDto)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<UserShortDto> findFollowers(long userId) {
        return jpaQueryFactory
            .selectFrom(qSubscription)
            .leftJoin(qSubscription.following).fetchJoin()
            .leftJoin(qSubscription.follower).fetchJoin()
            .where(qSubscription.following.id.eq(userId))
            .stream()
            .map(Subscription::getFollower)
            .map(userMapper::toUserShortDto)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public SubscriptionDto follow(long userId, long followingId) {
        User follower = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new NotFoundException("Пользователь с id = " + followingId + " не найден"));
        Subscription subscription = new Subscription();

        return subscriptionMapper.toSubscriptionShortDto(subscriptionRepository.save(
            subscriptionMapper.toSubscription(subscription, follower, following))
        );
    }

    @Override
    @Transactional
    public void unfollow(long userId, long followingId) {
        subscriptionRepository.deleteByFollowingId(followingId);
    }
}
