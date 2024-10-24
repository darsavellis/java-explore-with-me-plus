package ewm.compilation.mappers;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.dto.NewCompilationDto;
import ewm.compilation.dto.UpdateCompilationRequest;
import ewm.compilation.model.Compilation;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {
    @Mapping(target = "id", ignore = true)
    Compilation toCompilation(CompilationDto compilationDto);

    CompilationDto toCompilationDto(Compilation compilation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "compilationDto.title")
    @Mapping(target = "pinned", source = "compilationDto.pinned")
    @Mapping(target = "events", source = "events")
    Compilation toUpdateCompilation(
            @MappingTarget Compilation compilation,
            UpdateCompilationRequest compilationDto,
            List<Event> events
    );

    @Mapping(target = "events", source = "events")
    Compilation toCompilation(NewCompilationDto compilationDto, List<Event> events);

}