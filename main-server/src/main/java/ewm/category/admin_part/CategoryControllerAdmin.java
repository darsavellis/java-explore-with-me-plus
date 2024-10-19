package ewm.category.admin_part;

import ewm.category.admin_part.service.CategoryServiceAdmin;
import ewm.category.dto.CategoryDto;
import ewm.category.dto.NewCategoryDto;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryControllerAdmin {

    final CategoryServiceAdmin serviceAdmin;

    @PostMapping(path = "/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        return serviceAdmin.addNewCategory(categoryDto);
    }

    @DeleteMapping(path = "/categories/{catID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catID") Long catId) {
        serviceAdmin.deleteCategory(catId);
    }


    @PatchMapping("/categories/{catID}")
    public CategoryDto updateCategory(@PathVariable("catID") Long catID,
                                      @RequestBody NewCategoryDto newCategoryDto) {
        return serviceAdmin.updateCategoryById(catID, newCategoryDto);
    }


}
