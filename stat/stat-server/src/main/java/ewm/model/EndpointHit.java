package ewm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    Long id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
