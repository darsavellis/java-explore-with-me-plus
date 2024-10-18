package ewm.event.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublicEventParam {
    String text;
    List<Long> categories;
    boolean paid;
    String rangeStart;
    String rangeEnd;
    boolean onlyAvailable;
    String sort;
    int from;
    int size;
}
