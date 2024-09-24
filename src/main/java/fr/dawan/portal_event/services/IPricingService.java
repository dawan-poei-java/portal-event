package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.PricingDto;

public interface IPricingService {
    List<PricingDto> getAll();
    PricingDto getById(long id);
    PricingDto saveOrUpdate(PricingDto dto);
    void deleteById(long id);
}
