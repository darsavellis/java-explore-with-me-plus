package ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewm.event.model.Location;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewEventDto {
    @NotBlank
    @Length(min = 20, max = 2000)
    String annotation;
    Long category;
    @NotBlank
    @Length(min = 20, max = 7000)
    String description;
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    @Positive
    Integer participantLimit;
    Boolean requestModeration;
    @Length(min = 3, max = 120)
    String title;
}
