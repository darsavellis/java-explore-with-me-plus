package ewm.category.mapper;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.model.Category;
import ewm.event.mappers.EventMapper;
import ewm.user.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);

    NewCategoryDto toNewCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toCategory(NewCategoryDto newCategoryDto);
}
