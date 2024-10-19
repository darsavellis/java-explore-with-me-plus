package ewm.category.admin_part.service;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;

public interface CategoryServiceAdmin {

    CategoryDto add(NewCategoryDto categoryDto);

    void deleteBy(long id);

    CategoryDto updateBy(long id, NewCategoryDto newCategoryDto);
}
