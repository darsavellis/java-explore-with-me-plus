package ewm.event.dto;

import ewm.validation.StartAndEndValid;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@StartAndEndValid
public class PublicEventParam {
    String text;
    List<Long> categories;
    Boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeEnd;
    boolean onlyAvailable;
    String sort;
    int from = 0;
    int size = 10;
}
