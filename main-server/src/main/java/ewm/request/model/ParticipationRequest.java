package ewm.request.model;

import ewm.event.model.Event;
import ewm.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    Event event;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    User requester;
    @Enumerated(EnumType.STRING)
    RequestStatus status = RequestStatus.PENDING;
}
