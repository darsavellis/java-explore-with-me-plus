package ewm.client;

import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatRestClientImpl implements StatRestClient {
    final RestClient restClient;

    public StatRestClientImpl(@Value("${stat-server.uri}") String baseUri) {
        this.restClient = RestClient.builder()
            .baseUrl(baseUri)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .requestFactory(customRequestFactory())
            .build();
    }

    public void addHit(EndpointHitDto hitDto) {
        try {
            restClient.post().uri("/hit").body(hitDto).retrieve();
        } catch (Exception e) {
            log.info("Ошибка при обращении к эндпоинту /hit {}", e.getMessage(), e);
        }
    }

    public List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        try {
            return restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/stats")
                    .queryParam("start", start)
                    .queryParam("end", end)
                    .queryParam("uris", String.join(",", uris))
                    .queryParam("unique", unique)
                    .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        } catch (Exception e) {
            log.info("Ошибка при запросе к эндпоинту /stats {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private ClientHttpRequestFactory customRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(Duration.ofSeconds(10))
            .withReadTimeout(Duration.ofSeconds(10));
        return ClientHttpRequestFactories.get(settings);
    }
}
