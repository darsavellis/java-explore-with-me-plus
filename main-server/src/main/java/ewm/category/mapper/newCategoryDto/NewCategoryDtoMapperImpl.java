package ewm.category.mapper.newCategoryDto;

import ewm.category.dto.NewCategoryDto;
import ewm.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class NewCategoryDtoMapperImpl implements NewCategoryDtoMapper {
    @Override
    public NewCategoryDto toNewCategoryDto(Category category) {

        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName(category.getName());

        return newCategoryDto;
    }

    @Override
    public Category toCategory(NewCategoryDto newCategoryDto) {

        Category category = new Category();
        category.setName(newCategoryDto.getName());

        return category;
    }
}
