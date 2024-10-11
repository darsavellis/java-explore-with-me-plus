package ewm.mappers;

import ewm.dto.EndpointHitDto;
import ewm.model.EndpointHit;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndPointHitMapper {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitDto mapToEndpointHitDto(EndpointHit endpointHit) {
        EndpointHitDto dto = new EndpointHitDto();
        dto.setApp(endpointHit.getApp());
        dto.setIp(endpointHit.getIp());
        dto.setUri(endpointHit.getUri());
        dto.setTimestamp(endpointHit.getTimestamp().format(dateTimeFormatter));
        return dto;
    }

    public EndpointHit mapToEndpointHit(EndpointHitDto dto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(dto.getApp());
        endpointHit.setIp(dto.getIp());
        endpointHit.setUri(dto.getUri());
        LocalDateTime timestamp = LocalDateTime.parse(dto.getTimestamp(), dateTimeFormatter);
        endpointHit.setTimestamp(timestamp);
        return endpointHit;
    }
}
