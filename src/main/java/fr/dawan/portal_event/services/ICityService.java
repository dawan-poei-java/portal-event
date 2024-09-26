package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.CityDto;

public interface ICityService {

    List<CityDto> getAll();
    CityDto getById(long id);
    CityDto getByName(String name);
    CityDto saveOrUpdate(CityDto dto);
    void deleteById(long id);
}
