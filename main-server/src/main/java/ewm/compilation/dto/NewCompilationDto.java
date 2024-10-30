package ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    List<Long> events;
    boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}
