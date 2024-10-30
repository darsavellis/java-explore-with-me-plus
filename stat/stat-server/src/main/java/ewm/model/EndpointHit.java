package ewm.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "hits")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "app", length = 10000)
    String app;
    @Column(name = "uri", length = 10000)
    String uri;
    @Column(name = "ip", length = 10000)
    String ip;
    @Column(name = "timestamp")
    LocalDateTime timestamp;
}
