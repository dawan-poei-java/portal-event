package fr.dawan.portal_event.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.dawan.portal_event.dto.CityDto;

public class CityDtoConverterTest {
    private CityDtoConverter cityDtoConverter;

    @BeforeEach
    void setUp(){
        this.cityDtoConverter = new CityDtoConverter();
    }

    @Test
    void testConvert(){
        String cityName = "Montpellier";

        CityDto cityDto = cityDtoConverter.convert(cityName);

        assertNotNull(cityDto);
        assertEquals(cityName, cityDto.getName());
    }

    @Test
    void testConvertWithEmptyString(){
        String cityName = "";

        CityDto cityDto = cityDtoConverter.convert(cityName);

        assertNotNull(cityDto);
        assertEquals(cityName, cityDto.getName());
    }
}
