package ewm.compilation.service.impl;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.dto.NewCompilationDto;
import ewm.compilation.dto.UpdateCompilationRequest;
import ewm.compilation.mappers.CompilationMapper;
import ewm.compilation.model.Compilation;
import ewm.compilation.repository.CompilationRepository;
import ewm.compilation.service.CompilationServiceAdmin;
import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.exception.NotFoundException;
import ewm.exception.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationServiceAdminImpl implements CompilationServiceAdmin {

    final CompilationRepository repository;
    final CompilationMapper compilationMapper;
    final EventRepository eventRepository;

    @Override
    public CompilationDto add(NewCompilationDto compilationDto) {

        if (compilationDto.getTitle() == null || compilationDto.getTitle().isBlank()) {
            throw new ValidationException("Поле title не может быть пустой или состоять из пробела");
        }

        List<Event> events = eventRepository.findByIdList(compilationDto.getEvents());
        return compilationMapper.toCompilationDto(repository
            .save(compilationMapper.toCompilation(compilationDto, events)));
    }

    @Override
    public void deleteBy(long id) {
        repository.deleteById(id);
    }

    @Override
    public CompilationDto updateBy(long id, UpdateCompilationRequest compilationDto) {
        Compilation compilation = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Событие с id = " + id + " не найдено"));

        List<Event> events = eventRepository.findByIdList(compilationDto.getEvents());
        return compilationMapper.toCompilationDto(repository
            .save(compilationMapper.toUpdateCompilation(compilation, compilationDto, events)));
    }
}
