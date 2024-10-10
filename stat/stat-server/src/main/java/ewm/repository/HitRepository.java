package ewm.repository;

import ewm.dto.ViewStatsDto;
import ewm.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select new ewm.dto.ViewStatsDto(eh.app, eh.uri, count(eh.ip)) " +
            "from EndpointHit eh " +
            "where eh.timestamp >= :start and eh.timestamp <= :end " +
            "group by eh.uri, eh.app")
    List<ViewStatsDto> getAllStats(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    @Query(value = "select new ewm.dto.ViewStatsDto(eh.app, eh.uri, count(distinct(eh.ip))) " +
            "from EndpointHit eh " +
            "where eh.timestamp >= :start and eh.timestamp <= :end " +
            "group by eh.uri, eh.app")
    List<ViewStatsDto> getAllStatsUniqueIp(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);


    @Query(value = "select new ewm.dto.ViewStatsDto(eh.app, eh.uri, count(eh.ip)) " +
            "from EndpointHit eh " +
            "where eh.timestamp >= :start and eh.timestamp <= :end " +
            "group by eh.uri, eh.app having eh.uri in :uris")
    List<ViewStatsDto> getStats(@Param("uris") List<String> uris,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query(value = "select new ewm.dto.ViewStatsDto(eh.app, eh.uri, count(distinct(eh.ip))) " +
            "from EndpointHit eh " +
            "where eh.timestamp >= :start and eh.timestamp <= :end " +
            "group by eh.uri, eh.app having eh.uri in :uris")
    List<ViewStatsDto> getStatsUniqueIp(@Param("uris") List<String> uris,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

}
