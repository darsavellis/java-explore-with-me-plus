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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatServiceImpl implements StatService {
    final HitRepository hitRepository;
    final EndPointHitMapper hitMapper;

    @Override
    @Transactional
    public void hit(EndpointHitDto endpointHitDto) {
        log.info("Запись {} в БД", endpointHitDto);
        EndpointHit endpointHit = hitMapper.mapToEndpointHit(endpointHitDto);
        hitRepository.deleteAll();
        hitRepository.save(endpointHit);
        hitRepository.flush();
        log.info("Сохраненные данные: {}", hitRepository.findAll());
        log.info("Через статистику {}", stats(new RequestParamDto(LocalDateTime.now().minusYears(10), LocalDateTime.now().plusYears(10), List.of(endpointHitDto.getUri()), true)));
        log.info("Через статистику {}", stats(new RequestParamDto(LocalDateTime.now().minusYears(10), LocalDateTime.now().plusYears(10), List.of(endpointHitDto.getUri()), true)));
        log.info("Объект {} успешно сохранен в БД", endpointHit);
    }

    @Override
    @Transactional(readOnly = true)
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
