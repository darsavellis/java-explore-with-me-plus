package ewm.category.service.impl;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.repository.CategoryRepository;
import ewm.category.service.AdminCategoryService;
import ewm.exeption.NotFoundException;
import ewm.exeption.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    final CategoryRepository repository;

    @Override
    public CategoryDto add(NewCategoryDto categoryDto) {
        validation(categoryDto);
        return CategoryMapper.toCategoryDto(repository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Override
    public void deleteBy(long id) {
        repository.deleteById(id);
    }

    @Override
    public CategoryDto updateBy(long id, NewCategoryDto categoryDto) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));

        category.setName(categoryDto.getName());

        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    private void validation(NewCategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new ValidationException("Невозможно создать категорию с пустым названием");
        } else if (categoryDto.getName().isBlank()) {
            throw new ValidationException("Невозможно создать категорию состоящую только из пробелов");
        }
    }
}
