package ewm.category.admin_part.service;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.category.repository.RepositoryCategory;
import ewm.exeption.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {

    final RepositoryCategory repository;

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
        Optional<Category> category = repository.findById(id);

        if (category.isPresent()) {
            category.get().setName(categoryDto.getName());
        }

        return CategoryMapper.toCategoryDto(repository.save(category.get()));
    }

    private void validation(NewCategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new ValidationException("Невозможно создать категорию с пустым названием");
        } else if (categoryDto.getName().isBlank()) {
            throw new ValidationException("Невозможно создать категорию состоящую только из пробелов");
        }

    }
}

