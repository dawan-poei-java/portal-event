package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.TypeEventDto;
import fr.dawan.portal_event.entities.TypeEvent;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TypeEventMapper {
    TypeEventDto toDto(TypeEvent typeEvent);
    TypeEvent toEntity(TypeEventDto typeEventDto);
}
