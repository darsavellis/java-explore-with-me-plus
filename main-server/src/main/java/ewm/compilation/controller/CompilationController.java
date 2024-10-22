package ewm.compilation.controller;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.service.impl.CompilationService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
                                       @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getBy(@PositiveOrZero @PathVariable(required = true)  long compId) {
        return compilationService.getBy(compId);
    }

}
