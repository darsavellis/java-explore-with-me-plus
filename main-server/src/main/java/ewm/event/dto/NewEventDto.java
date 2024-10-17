package ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewEventDto {
    @Size(max = 2000, min = 20)
    String annotation;
    Long category;
    @Size(max = 7000, min = 20)
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    Integer participantLimit;
    boolean requestModeration;
    @Size(max = 120, min = 3)
    String title;
}
