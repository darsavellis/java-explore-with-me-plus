package ewm.service;

import ewm.dto.EndpointHitDto;
import ewm.dto.RequestParamDto;
import ewm.dto.ViewStatsDto;
import ewm.model.EndpointHit;

import java.util.List;

public interface StatService {
    EndpointHitDto hit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> stats(RequestParamDto params);
}
