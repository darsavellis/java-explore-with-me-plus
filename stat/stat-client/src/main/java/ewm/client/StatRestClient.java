package ewm.client;

import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class StatRestClient {

//    @Value("${spring.baseurl}")
//    String url;

    final WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:9090")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:9090"))
        .build();

    public void addHit(EndpointHitDto hitDto) {

        Mono<EndpointHitDto> response = webClient.post()
            .uri("/hit")
            .body(Mono.just(hitDto), EndpointHitDto.class)
            .retrieve()
            .bodyToMono(EndpointHitDto.class);
    }


    public List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/state")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", String.join(",", uris))
                .queryParam("unique", unique)
                .build()
            )
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<ViewStatsDto>>() {
            })
            .block();

    }

}
