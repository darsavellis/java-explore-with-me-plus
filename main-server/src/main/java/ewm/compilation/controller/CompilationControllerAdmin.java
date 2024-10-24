package ewm.compilation.controller;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.dto.NewCompilationDto;
import ewm.compilation.dto.UpdateCompilationRequest;
import ewm.compilation.service.CompilationServiceAdmin;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(path = "/admin/compilations")
public class CompilationControllerAdmin {

    final CompilationServiceAdmin serviceAdmin;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto add(@Valid @RequestBody NewCompilationDto compilationDto) {
        return serviceAdmin.add(compilationDto);
    }

    @DeleteMapping(path = "/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBy(@PathVariable("comId") long id) {
        serviceAdmin.deleteBy(id);
    }

    @PatchMapping(path = "/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateBy(@PathVariable("comId") long id,
                                   @Valid @RequestBody UpdateCompilationRequest compilationDto) {
        return serviceAdmin.updateBy(id, compilationDto);
    }
}
