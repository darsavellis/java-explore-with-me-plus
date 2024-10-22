package ewm.request.dto;

import ewm.request.model.RequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    RequestStatus status;
}
