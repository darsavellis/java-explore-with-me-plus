package ewm.service;

import ewm.dto.EndpointHitDto;
import ewm.dto.RequestParamDto;
import ewm.dto.ViewStatsDto;
import ewm.mappers.EndPointHitMapper;
import ewm.model.EndpointHit;
import ewm.repository.HitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatServiceImpl implements StatService {
    final HitRepository hitRepository;
    final EndPointHitMapper hitMapper;

    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        log.info("Запись {} в БД", endpointHitDto);
        EndpointHit endpointHit = hitMapper.mapToEndpointHit(endpointHitDto);
        hitRepository.save(endpointHit);
        log.info("Объект {} успешно сохранен в БД", endpointHit);
    }

    @Override
    public List<ViewStatsDto> stats(RequestParamDto params) {
        log.info("Запрос статистики {}", params);
        List<ViewStatsDto> statsToReturn;

        boolean paramsIsExists = params.getUris() == null || params.getUris().isEmpty();

        if (!params.getUnique()) {
            if (paramsIsExists) {
                statsToReturn = hitRepository.getAllStats(params.getStart(), params.getEnd());
            } else {
                statsToReturn = hitRepository.getStats(params.getUris(), params.getStart(), params.getEnd());
            }
        } else {
            if (paramsIsExists) {
                statsToReturn = hitRepository.getAllStatsUniqueIp(params.getStart(), params.getEnd());
            } else {
                statsToReturn = hitRepository.getStatsUniqueIp(params.getUris(), params.getStart(), params.getEnd());
            }
        }

        log.info("Данные статистики {} успешно считаны из БД", statsToReturn);
        return statsToReturn;
    }
}
