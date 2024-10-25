package ewm.category.service.impl;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.repository.CategoryRepository;
import ewm.category.service.AdminCategoryService;
import ewm.exception.NotFoundException;
import ewm.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    final CategoryRepository repository;
    final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto add(NewCategoryDto categoryDto) {
        validation(categoryDto);
        return categoryMapper.toCategoryDto(repository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void deleteBy(long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto updateBy(long id, NewCategoryDto categoryDto) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));

        category.setName(categoryDto.getName());

        return categoryMapper.toCategoryDto(repository.save(category));
    }

    private void validation(NewCategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new ValidationException("Невозможно создать категорию с пустым названием");
        } else if (categoryDto.getName().isBlank()) {
            throw new ValidationException("Невозможно создать категорию состоящую только из пробелов");
        }
    }
}

