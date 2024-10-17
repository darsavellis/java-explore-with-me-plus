package ewm.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewUserRequest {
    @Size(max = 254, min = 6)
    String email;
    @Size(max = 250, min = 2)
    String name;
}
