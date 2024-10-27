package ewm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@ToString
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
