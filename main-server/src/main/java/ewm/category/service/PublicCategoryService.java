package ewm.category.service;

import ewm.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getAll(int from, int size);

    CategoryDto getBy(long id);
}
