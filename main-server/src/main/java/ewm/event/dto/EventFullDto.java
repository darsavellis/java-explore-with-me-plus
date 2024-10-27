package ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewm.category.dto.CategoryDto;
import ewm.event.model.EventState;
import ewm.event.model.Location;
import ewm.user.dto.UserShortDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class EventFullDto {
    Long id;
    String annotation;
    CategoryDto category;
    long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    UserShortDto initiator;
    Location location;
    boolean paid;
    int participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;
    boolean requestModeration;
    EventState state;
    String title;
    long views;
}
