package ewm.category.controller;

import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import ewm.category.service.AdminCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCategoryController {
    final AdminCategoryService serviceAdmin;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto add(@Validated @RequestBody NewCategoryDto categoryDto) {
        return serviceAdmin.add(categoryDto);
    }

    @DeleteMapping(path = "/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("catId") long catId) {
        serviceAdmin.deleteBy(catId);
    }

    @PatchMapping(path = "/{catId}")
    public CategoryDto updateBy(@PathVariable("catId") long catId,
                                @Validated @RequestBody NewCategoryDto newCategoryDto) {
        return serviceAdmin.updateBy(catId, newCategoryDto);
    }
}
