package ewm.category.mapper;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }

    public NewCategoryDto toNewCategoryDto(Category category) {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName(category.getName());

        return newCategoryDto;
    }

    public Category toCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());

        return category;
    }
}
