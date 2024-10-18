package ewm.event.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminEventParam {
    List<Long> users;
    List<String> states;
    List<Long> categories;
    String rangeStart;
    String rangeEnd;
    int from;
    int size;
}
