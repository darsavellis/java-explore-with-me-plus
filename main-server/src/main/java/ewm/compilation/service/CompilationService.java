package ewm.compilation.service;

import ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto getBy(Long id);
}