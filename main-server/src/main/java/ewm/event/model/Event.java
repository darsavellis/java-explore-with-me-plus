package ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewm.category.model.Category;
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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "annotation", length = 2000)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @Column(name = "confirmed_requests")
    Long confirmedRequests;
    @Column(name = "created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn = LocalDateTime.now();
    @Column(name = "description", length = 7000)
    String description;
    @Column(name = "event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;
    @Embedded
    Location location;
    @Column(name = "paid")
    Boolean paid;
    @Column(name = "participant_limit")
    int participantLimit;
    @Column(name = "published_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    Boolean requestModeration;
    @Enumerated
    @Column(name = "state", length = 50)
    EventState state = EventState.PENDING;
    @Column(name = "title", length = 120)
    String title;
    @Column(name = "views")
    Long views;
}