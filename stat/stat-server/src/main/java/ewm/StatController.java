package ewm;

import ewm.dto.EndpointHitDto;
import ewm.dto.RequestParamDto;
import ewm.dto.ViewStatsDto;
import ewm.service.StatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatController {
    private final StatService statService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void hit(@RequestBody EndpointHitDto endpointHitDto) {
        statService.hit(endpointHitDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stats")
    public List<ViewStatsDto> stats(@RequestParam LocalDateTime start,
                                    @RequestParam LocalDateTime end,
                                    @RequestParam List<String> uris,
                                    @RequestParam Boolean unique) {
        RequestParamDto requestParamDto = new RequestParamDto(start, end, uris, unique);
        return statService.stats(requestParamDto);
    }
}
