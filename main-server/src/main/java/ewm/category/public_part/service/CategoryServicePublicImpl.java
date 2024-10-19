package ewm.category.public_part.service;

import ewm.category.model.Category;
import ewm.category.repository.RepositoryCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServicePublicImpl implements CategoryServicePublic {

    final RepositoryCategory repositoryCategory;

    @Override
    public List<Category> getAllCategory(Long limit) {
        return repositoryCategory.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return repositoryCategory.findById(id);
    }
}
