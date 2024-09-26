package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CategoryDto;
import fr.dawan.portal_event.entities.Category;
import fr.dawan.portal_event.repositories.CategoryRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> result = new ArrayList<CategoryDto>();

        for(Category category: categories){
            CategoryDto dto = DtoTool.convert(category, CategoryDto.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public CategoryDto getById(long id) {
        return DtoTool.convert(categoryRepository.findById(id).get(), CategoryDto.class);
    }

    @Override
    public CategoryDto saveOrUpdate(CategoryDto dto) {
        Category category = DtoTool.convert(dto, Category.class);
        return DtoTool.convert(categoryRepository.saveAndFlush(category), CategoryDto.class);
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

}
