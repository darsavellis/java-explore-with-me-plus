package ewm.service;

import ewm.dto.EndpointHitDto;
import ewm.dto.RequestParamDto;
import ewm.dto.ViewStatsDto;
import ewm.exceptions.InvalidDataException;
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

    @Override
    public EndpointHitDto hit(EndpointHitDto endpointHitDto) {
        log.info("Запись {} в БД", endpointHitDto);

        EndpointHit endpointHit = EndPointHitMapper.mapToEndpointHit(endpointHitDto);
        log.info("Объект {} успешно сохранен в БД", endpointHit);
        return EndPointHitMapper.mapToEndpointHitDto(hitRepository.save(endpointHit));
    }

    @Override
    public List<ViewStatsDto> stats(RequestParamDto params) {
        log.info("Запрос статистики {}", params);
        List<ViewStatsDto> statsToReturn;

        if (params.getUris() != null) {
            for (String uri : params.getUris()) {
                if (uri.isEmpty()) {
                    throw new InvalidDataException("Uri не может быть пустой строкой");
                }
            }
        }

        if (!params.getUnique()) {
            if (params.getUris() == null) {
                statsToReturn = hitRepository.getAllStats(params.getStart(), params.getEnd());
            } else {
                statsToReturn = hitRepository.getStats(params.getUris(), params.getStart(), params.getEnd());
            }
        } else {
            if (params.getUris() == null) {
                statsToReturn = hitRepository.getAllStatsUniqueIp(params.getStart(), params.getEnd());
            } else {
                statsToReturn = hitRepository.getStatsUniqueIp(params.getUris(), params.getStart(), params.getEnd());
            }
        }

        log.info("Данные статистики {} успешно считаны из БД", statsToReturn);
        return statsToReturn;
    }
}
