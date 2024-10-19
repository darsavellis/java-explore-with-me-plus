package ewm.category.public_part.service;

import ewm.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryServicePublic {
    List<Category> getAll(int from, int size);

    Optional<Category> getBy(long id);
}
