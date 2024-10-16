package ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RequestParamDto {
    LocalDateTime start;
    LocalDateTime end;
    List<String> uris;
    Boolean unique;
}
