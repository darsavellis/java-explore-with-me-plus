package ewm.event.dto;

import ewm.event.model.UserStateAction;
import ewm.validation.EventDateInTwoHours;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UpdateEventUserRequest extends UpdateEventRequest {
    UserStateAction stateAction;
    @EventDateInTwoHours
    LocalDateTime eventDate;
}
