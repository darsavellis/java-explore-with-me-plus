package ewm.compilation.mappers;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.model.Compilation;
import ewm.event.mappers.EventMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {
    @Mapping(target = "id", ignore = true)
    Compilation toCompilation(CompilationDto compilationDto);

    CompilationDto toCompilationDto(Compilation compilation);

}