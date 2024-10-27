package ewm.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class NewCategoryDto {
    @NotBlank
    @Length(min = 1, max = 50)
    String name;
}
