package ewm.compilation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CompilationDto {
    Long id;
    List<Long> events;
    boolean pinned;
    String title;
}
