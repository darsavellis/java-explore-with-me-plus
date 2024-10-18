package ewm.category.admin_part.service;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.mapper.categoryDto.CategoryDtoMapper;
import ewm.category.mapper.newCategoryDto.NewCategoryDtoMapper;
import ewm.category.model.Category;
import ewm.category.repository.RepositoryCategory;
import ewm.exeption.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {

    final RepositoryCategory repository;
    final NewCategoryDtoMapper newCategoryDtoMapper;
    final CategoryDtoMapper categoryDtoMapper;

    @Override
    public CategoryDto addNewCategory(NewCategoryDto categoryDto) {
        validation(categoryDto);
        return categoryDtoMapper.toCategoryDto(repository.save(newCategoryDtoMapper.toCategory(categoryDto)));
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CategoryDto updateCategoryById(Long id, NewCategoryDto categoryDto) {
        Optional<Category> category = repository.findById(id);

        if (category.isPresent()) {
            category.get().setName(categoryDto.getName());
        }

        return categoryDtoMapper.toCategoryDto(repository.save(category.get()));
    }

    private void validation(NewCategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new ValidationException("Невозможно создать категорию с пустым названием");
        } else if (categoryDto.getName().isBlank()) {
            throw new ValidationException("Невозможно создать категорию состоящую только из пробелов");
        }

    }
}

