package ewm.event.dto;

import ewm.event.model.UserStateAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateEventUserRequest extends UpdateEventRequest {
    UserStateAction stateAction;
}
