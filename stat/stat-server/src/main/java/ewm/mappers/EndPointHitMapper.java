package ewm.mappers;

import ewm.dto.EndpointHitDto;
import ewm.model.EndpointHit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EndPointHitMapper {
    public EndpointHitDto mapToEndpointHitDto(EndpointHit endpointHit) {
        EndpointHitDto dto = new EndpointHitDto();
        dto.setApp(endpointHit.getApp());
        dto.setIp(endpointHit.getIp());
        dto.setUri(endpointHit.getUri());
        dto.setTimestamp(endpointHit.getTimestamp());
        return dto;
    }

    public EndpointHit mapToEndpointHit(EndpointHitDto dto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(dto.getApp());
        endpointHit.setIp(dto.getIp());
        endpointHit.setUri(dto.getUri());
        endpointHit.setTimestamp(dto.getTimestamp());
        return endpointHit;
    }
}
