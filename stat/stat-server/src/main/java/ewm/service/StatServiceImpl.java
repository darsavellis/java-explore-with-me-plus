package ewm.service;

import ewm.dto.EndpointHitDto;
import ewm.dto.RequestParamDto;
import ewm.dto.ViewStatsDto;
import ewm.mappers.EndPointHitMapper;
import ewm.repository.HitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatServiceImpl implements StatService {
    final HitRepository hitRepository;

    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        hitRepository.save(EndPointHitMapper.mapToEndpointHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> stats(RequestParamDto params) {

        return List.of();
    }
}
