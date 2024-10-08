package ewm.repository;

import ewm.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHit, Long> {
    List<EndpointHit> findAllByTimestampAfterAndBeforeAndUriInIpIsUnique(LocalDateTime start, LocalDateTime end,
                                                                         List<String> uris, String ip);
}
