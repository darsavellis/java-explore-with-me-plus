package ewm.category.mapper.newCategoryDto;

import ewm.category.dto.NewCategoryDto;
import ewm.category.model.Category;

public interface NewCategoryDtoMapper {
    NewCategoryDto toNewCategoryDto(Category category);

    Category toCategory(NewCategoryDto newCategoryDto);
}
