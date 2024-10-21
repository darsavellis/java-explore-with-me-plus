package ewm.category.mapper.categoryDto;

import ewm.category.dto.CategoryDto;
import ewm.category.model.Category;

public interface CategoryDtoMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);
}