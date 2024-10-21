package ewm.event.dto;

import ewm.event.model.AdminStateAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateEventAdminRequest extends UpdateEventRequest {
    AdminStateAction stateAction;
}
