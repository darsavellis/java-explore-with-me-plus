package ewm.category.service.impl;

import ewm.category.dto.CategoryDto;
import ewm.category.mapper.CategoryMapper;
import ewm.category.repository.CategoryRepository;
import ewm.category.service.PublicCategoryService;
import ewm.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return categoryRepository.findAll(pageable).map(CategoryMapper::toCategoryDto).getContent();
    }

    @Override
    public CategoryDto getBy(long id) {
        return categoryRepository.findById(id).map(CategoryMapper::toCategoryDto)
            .orElseThrow(() -> new NotFoundException("Категория с id = " + id + " не найдена"));
    }
}
