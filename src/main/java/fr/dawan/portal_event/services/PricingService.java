package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.PricingDto;
import fr.dawan.portal_event.entities.Pricing;
import fr.dawan.portal_event.repositories.PricingRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class PricingService implements IPricingService{

    @Autowired
    private PricingRepository pricingRepository;

    @Override
    public List<PricingDto> getAll() {
        List<Pricing> pricings = pricingRepository.findAll();
        List<PricingDto> result = new ArrayList<PricingDto>();  

        for(Pricing pricing: pricings){
            PricingDto dto = DtoTool.convert(pricing, PricingDto.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public PricingDto getById(long id) {
        return DtoTool.convert(pricingRepository.findById(id).get(), PricingDto.class);
    }

    @Override
    public PricingDto saveOrUpdate(PricingDto dto) {
        Pricing pricing = DtoTool.convert(dto, Pricing.class);
        return DtoTool.convert(pricingRepository.saveAndFlush(pricing), PricingDto.class);
    }

    @Override
    public void deleteById(long id) {
        pricingRepository.deleteById(id);
    }

}
