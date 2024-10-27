package ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ewm.category.dto.CategoryDto;
import ewm.user.dto.UserShortDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class EventShortDto {
    Long id;
    String annotation;
    CategoryDto category;
    long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    UserShortDto initiator;
    boolean paid;
    String title;
    long views;
}
