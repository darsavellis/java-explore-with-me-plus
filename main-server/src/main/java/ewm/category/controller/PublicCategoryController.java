package ewm.category.controller;

import ewm.category.dto.CategoryDto;
import ewm.category.service.PublicCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicCategoryController {
    final PublicCategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAllBy(@RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAllBy(PageRequest.of(from, size));
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto findBy(@PathVariable("catId") long catId) {
        return categoryService.getBy(catId);
    }
}
