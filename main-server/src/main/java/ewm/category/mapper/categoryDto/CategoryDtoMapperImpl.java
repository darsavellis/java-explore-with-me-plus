package ewm.category.mapper.categoryDto;

import ewm.category.dto.CategoryDto;
import ewm.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapperImpl implements CategoryDtoMapper {
    @Override
    public CategoryDto toCategoryDto(Category category) {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    @Override
    public Category toCategory(CategoryDto categoryDto) {

        Category category = new Category();
        categoryDto.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }
}
