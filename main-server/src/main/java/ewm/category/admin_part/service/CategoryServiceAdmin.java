package ewm.category.admin_part.service;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;

public interface CategoryServiceAdmin {

    CategoryDto addNewCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategoryById(Long id, NewCategoryDto newCategoryDto);
}
