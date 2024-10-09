package ewm.controller;

import ewm.client.StatRestClient;
import ewm.dto.EndpointHitDto;
import ewm.dto.ViewStatsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientController {

    final StatRestClient statRestClient;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody EndpointHitDto hitDto) {
        statRestClient.addHit(hitDto);
    }

    @GetMapping(path = "/state")
    public List<ViewStatsDto> getStateByParam(@RequestParam LocalDateTime start,
                                              @RequestParam LocalDateTime end,
                                              @RequestParam(required = false) List<String> uris,
                                              @RequestParam(defaultValue = "false") Boolean unique) {
        return statRestClient.stats(start, end, uris, unique);
    }

}
