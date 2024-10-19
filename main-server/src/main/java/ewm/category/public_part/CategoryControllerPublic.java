package ewm.category.public_part;

import ewm.category.model.Category;
import ewm.category.public_part.service.CategoryServicePublic;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryControllerPublic {

    final CategoryServicePublic categoryService;

    @GetMapping
    public List<Category> getAll(@RequestParam(defaultValue = "0") int from,
                                 @RequestParam(defaultValue = "10") int size) {
        return categoryService.getAll(from, size);
    }

    @GetMapping(path = "/{catId}")
    public Category getBy(@PathVariable("catId") long catId) {
        return categoryService.getBy(catId).get();
    }
}
