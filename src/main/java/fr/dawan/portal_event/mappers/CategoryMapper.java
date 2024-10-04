package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.CategoryDto;
import fr.dawan.portal_event.entities.Category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
