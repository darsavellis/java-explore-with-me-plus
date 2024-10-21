package ewm.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class NewCategoryDto {
    @Length(min = 1, max = 50)
    String name;
}
