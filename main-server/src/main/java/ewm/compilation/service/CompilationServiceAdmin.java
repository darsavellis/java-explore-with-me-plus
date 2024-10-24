package ewm.compilation.service;


import ewm.compilation.dto.CompilationDto;
import ewm.compilation.dto.NewCompilationDto;
import ewm.compilation.dto.UpdateCompilationRequest;

public interface CompilationServiceAdmin {

    CompilationDto add(NewCompilationDto compilationDto);

    void deleteBy(long id);

    CompilationDto updateBy(long id, UpdateCompilationRequest compilationDto);
}
