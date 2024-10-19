package ewm.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class Location {
    @Column(name = "latitude")
    Float lat;
    @Column(name = "longitude")
    Float lon;
}
