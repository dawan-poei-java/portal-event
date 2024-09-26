package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.CategoryDto;

public interface ICategoryService {
    List<CategoryDto> getAll();
    CategoryDto getById(long id);
    CategoryDto saveOrUpdate(CategoryDto dto);
    void deleteById(long id);
}
