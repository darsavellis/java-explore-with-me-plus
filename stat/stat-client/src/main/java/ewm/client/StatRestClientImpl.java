package ewm.client;

import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatRestClientImpl implements StatRestClient {
    final RestTemplate restTemplate;

    public StatRestClientImpl(@Value("${stat-server.uri}") String baseUri) {
        this.restTemplate = new RestTemplateBuilder()
            .rootUri(baseUri)
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10))
            .build();
    }

    public void addHit(EndpointHitDto hitDto) {
        try {
            restTemplate.postForObject("/hit", hitDto, Void.class);
        } catch (Exception e) {
            log.info("Ошибка при обращении к эндпоинту /hit {}", e.getMessage(), e);
        }
    }

    public List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        try {
            String uri = UriComponentsBuilder.fromPath("/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", String.join(",", uris))
                .queryParam("unique", unique)
                .toUriString();

            ResponseEntity<List<ViewStatsDto>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
            return response.getBody();
        } catch (Exception e) {
            log.info("Ошибка при запросе к эндпоинту /stats {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
