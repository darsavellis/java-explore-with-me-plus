package ewm.dto;

import lombok.Data;

@Data
public class EndpointHitDto {
    //@NotEmpty
    String app;
    //@NotEmpty
    String uri;
    //@Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")
    String ip;
    String timestamp;
}
