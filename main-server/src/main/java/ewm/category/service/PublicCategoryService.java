package ewm.category.service;

import ewm.category.dto.CategoryDto;
import ewm.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface PublicCategoryService {
    List<CategoryDto> getAll(int from, int size);

    CategoryDto getBy(long id);
}
