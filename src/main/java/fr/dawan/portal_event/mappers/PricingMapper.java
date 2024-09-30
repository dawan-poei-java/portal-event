package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.PricingDto;
import fr.dawan.portal_event.entities.Pricing;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricingMapper {
    PricingDto toDto(Pricing pricing);
    Pricing toEntity(PricingDto pricingDto);
}
