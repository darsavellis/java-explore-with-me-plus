package ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewCompilationDto {
    List<Long> events;
    boolean pinned;
    @Size(max = 50, min = 1)
    String title;
}
