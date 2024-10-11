package ewm.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EndpointHitDto {
    //@NotEmpty
    String app;
    //@NotEmpty
    String uri;
    //@Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")
    String ip;
    //@PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String timestamp;
}
