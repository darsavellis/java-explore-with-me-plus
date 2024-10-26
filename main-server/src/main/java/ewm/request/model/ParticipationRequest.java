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
@Table(name = "requests", uniqueConstraints = @UniqueConstraint(columnNames = {"requester_id", "event_id"}))
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "created")
    LocalDateTime created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    Event event;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    User requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    RequestStatus status = RequestStatus.PENDING;
}
