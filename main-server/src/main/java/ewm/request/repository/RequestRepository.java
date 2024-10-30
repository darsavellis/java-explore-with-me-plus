package ewm.request.repository;

import ewm.request.model.ParticipationRequest;
import ewm.request.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(long userId);

    List<ParticipationRequest> findAllByEventIdAndEventInitiatorId(long eventId, long userId);

    List<ParticipationRequest> findAllByIdInAndEventIdIs(List<Long> eventIds, long eventId);

    long countAllByEventIdAndStatusIs(long eventId, RequestStatus status);

    @Query("select request.event.id as eventId, count(request) as confirmedRequests from ParticipationRequest as request " +
        "where request.event.id in :eventIds and request.status = :status " +
        "group by request.event.id")
    List<ConfirmedRequests> findAllByEventIdInAndStatusIs(List<Long> eventIds, RequestStatus status);
}
