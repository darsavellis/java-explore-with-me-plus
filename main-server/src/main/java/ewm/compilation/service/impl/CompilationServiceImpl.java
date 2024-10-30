package ewm.compilation.service.impl;

import ewm.compilation.dto.*;
import ewm.compilation.mappers.CompilationMapper;
import ewm.compilation.model.Compilation;
import ewm.compilation.repository.CompilationRepository;
import ewm.compilation.service.CompilationService;
import ewm.event.dto.EventShortDto;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.exception.NotFoundException;
import ewm.exception.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationServiceImpl implements CompilationService {
    final CompilationRepository compilationRepository;
    final CompilationMapper compilationMapper;
    final EventMapper eventMapper;
    final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAll(Boolean pinned, Pageable pageRequest) {
        Map<Long, List<Long>> compilationEventMap = compilationRepository.getCompilationEventMapping()
            .stream().collect(Collectors.groupingBy(
                CompilationEvent::getCompilationId,
                Collectors.mapping(CompilationEvent::getEventId, Collectors.toList()))
            );

        List<Long> eventIds = compilationEventMap.values()
            .stream()
            .flatMap(List::stream)
            .toList();

        Map<Long, EventShortDto> allEvents = eventRepository.findAllByIdIn(eventIds)
            .stream()
            .collect(Collectors.toMap(Event::getId, eventMapper::toEventShortDto));

        List<EmptyCompilation> compilations = compilationRepository.findAllByPinnedIs(pinned, pageRequest);

        return compilations
            .stream().map(compilationMapper::toCompilationDto)
            .peek(compilation -> compilation.setEvents(compilationEventMap.getOrDefault(
                compilation.getId(),
                Collections.emptyList()
            ).stream().map(allEvents::get).toList()))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getBy(Long id) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(id);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Подборка с id=" + id + " не найдена");
        }
        return compilationMapper.toCompilationDto(compilationOptional.get());
    }

    @Override
    @Transactional
    public CompilationDto add(NewCompilationDto compilationDto) {
        if (compilationDto.getTitle() == null || compilationDto.getTitle().isBlank()) {
            throw new ValidationException("Поле title не может быть пустой или состоять из пробела");
        }

        List<Event> events = eventRepository.findAllByIdIn(compilationDto.getEvents());
        return compilationMapper.toCompilationDto(compilationRepository
            .save(compilationMapper.toCompilation(compilationDto, events)));
    }

    @Override
    @Transactional
    public void deleteBy(long id) {
        compilationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CompilationDto updateBy(long id, UpdateCompilationRequest compilationDto) {
        Compilation compilation = compilationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Событие с id = " + id + " не найдено"));

        List<Event> events = eventRepository.findAllByIdIn(compilationDto.getEvents());
        return compilationMapper.toCompilationDto(compilationRepository
            .save(compilationMapper.toUpdateCompilation(compilation, compilationDto, events)));
    }
}
