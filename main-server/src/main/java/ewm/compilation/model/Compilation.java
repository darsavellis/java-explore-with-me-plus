package ewm.compilation.model;

import ewm.event.model.Event;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "compilations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "pinned")
    Boolean pinned;
    @Column(name = "title")
    String title;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compilations_events",
        joinColumns = {@JoinColumn(name = "compilation_id")},
        inverseJoinColumns = {@JoinColumn(name = "event_id")})
    Set<Event> events = new HashSet<>();
}
