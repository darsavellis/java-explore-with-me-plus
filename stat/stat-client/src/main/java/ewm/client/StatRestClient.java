package ewm.client;

import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatRestClient {
    final WebClient webClient;

    public StatRestClient(@Value("${stat-server.uri}") String baseUri) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUri)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<EndpointHitDto> addHit(EndpointHitDto hitDto) {
        return webClient.post()
            .uri("/hit")
            .bodyValue(hitDto)
            .retrieve()
            .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), ClientResponse::createException)
            .bodyToMono(EndpointHitDto.class);
    }

    public Mono<List<ViewStatsDto>> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", String.join(",", uris))
                .queryParam("unique", unique)
                .build())
            .retrieve()
            .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), ClientResponse::createException)
            .bodyToMono(new ParameterizedTypeReference<List<ViewStatsDto>>() {
            })
            .timeout(Duration.ofSeconds(100))
            .onErrorResume(throwable -> {
                log.info(throwable.getMessage(), throwable);
                return Mono.empty();
            });
    }
}
