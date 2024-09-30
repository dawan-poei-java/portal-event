package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.entities.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDto toDto(City city);
    City toEntity(CityDto cityDto);
}
