package ewm.category.public_part.service;

import ewm.category.model.Category;
import ewm.category.repository.RepositoryCategory;
import ewm.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServicePublicImpl implements CategoryServicePublic {

    final RepositoryCategory repositoryCategory;

    @Override
    public List<Category> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return repositoryCategory.findAll(pageable).getContent();
    }

    @Override
    public Optional<Category> getBy(long id) {
        return Optional.ofNullable(repositoryCategory.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + id + " не найдена")));
    }
}
