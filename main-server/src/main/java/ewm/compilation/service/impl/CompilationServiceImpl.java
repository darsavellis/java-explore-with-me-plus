package ewm.compilation.service.impl;

import com.querydsl.core.BooleanBuilder;
import ewm.compilation.dto.CompilationDto;
import ewm.compilation.mappers.CompilationMapper;
import ewm.compilation.model.Compilation;
import ewm.compilation.model.QCompilation;
import ewm.compilation.repository.CompilationRepository;
import ewm.compilation.service.CompilationService;
import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (pinned != null) {
            booleanBuilder.and(QCompilation.compilation.pinned.eq(pinned));
        }
        return compilationRepository.findAll(booleanBuilder, pageable).stream()
            .map(compilationMapper::toCompilationDto)
            .toList();
    }

    @Override
    public CompilationDto getBy(Long id) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(id);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Подборка с id=" + id + " не найдена");
        }
        return compilationMapper.toCompilationDto(compilationOptional.get());
    }
}
