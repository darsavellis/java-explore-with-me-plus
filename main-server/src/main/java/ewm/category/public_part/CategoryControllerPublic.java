package ewm.category.public_part;

import ewm.category.model.Category;
import ewm.category.public_part.service.CategoryServicePublic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryControllerPublic {

    final CategoryServicePublic categoryService;

    @GetMapping
    public List<Category> getAllCategory(@RequestParam(defaultValue = "10") Long limit) {
        return categoryService.getAllCategory(limit);
    }

    @GetMapping(path = "/{catId}")
    public Category getCategoryById(@PathVariable("catId") Long catId) {
        return categoryService.getCategoryById(catId).get();
    }
}
