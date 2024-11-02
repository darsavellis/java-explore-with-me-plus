package ewm.subscriptions.repository;

import ewm.subscriptions.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Set<Subscription> findAllByFollowerId(long followerId);

    Set<Subscription> findALlByFollowingId(long followingId);

    void deleteByFollowingId(long followingId);
}
