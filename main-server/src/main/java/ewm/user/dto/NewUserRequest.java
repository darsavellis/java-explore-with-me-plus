package ewm.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class NewUserRequest {
    @NotBlank
    @Length(min = 6, max = 254)
    String email;
    @NotBlank
    @Length(min = 2, max = 250)
    String name;
}
