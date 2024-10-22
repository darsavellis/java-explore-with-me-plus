package ewm.compilation.dto;

import ewm.event.model.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CompilationDto {
    Long id;
    List<Event> events;
    boolean pinned;
    String title;
}
