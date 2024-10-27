package ewm.event.dto;

import ewm.event.model.AdminStateAction;
import ewm.validation.EventDateInOneHour;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UpdateEventAdminRequest extends UpdateEventRequest {
    AdminStateAction stateAction;
    @EventDateInOneHour
    LocalDateTime eventDate;
}
