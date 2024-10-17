package ewm.category.public_part.service;

import java.util.List;
import java.util.Optional;

import ewm.category.model.Category;

public interface CategoryServicePublic {
    List<Category> getAllCategory(Long limit);

    Optional<Category> getCategoryById(Long id);
}
