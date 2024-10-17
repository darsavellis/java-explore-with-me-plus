package ewm.category.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewCategoryDto {
    @Size(max = 50, min = 1)
    String name;
}
