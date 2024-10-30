package ewm.event.repository;

import ewm.event.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    List<Event> findAllByInitiatorId(long userId, Pageable pageRequest);

    List<Event> findAllByIdIn(@Param("ids") List<Long> ids);

    boolean existsByCategoryId(long categoryId);
}
