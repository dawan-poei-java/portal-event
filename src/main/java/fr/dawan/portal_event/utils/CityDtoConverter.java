package fr.dawan.portal_event.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.dawan.portal_event.dto.CityDto;

@Component
public class CityDtoConverter implements Converter<String, CityDto> {



    @Override
    public CityDto convert(String city) {
        return new CityDto(city);
    }

}
