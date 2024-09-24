package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.repositories.CityRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class CityService implements ICityService{

    @Autowired
    private CityRepository cityRepository;

    public CityDto getById(long id){
        return DtoTool.convert(cityRepository.findById(id).get(), CityDto.class);
    }

    public CityDto getByName(String name){
        return DtoTool.convert(cityRepository.findByName(name), CityDto.class);
    }


    public List<CityDto> getAll(){
        List<City> cities = cityRepository.findAll();

        List<CityDto> result = new ArrayList<CityDto>();
        for(City city: cities){
            CityDto dto = DtoTool.convert(city, CityDto.class);
            result.add(dto);
        }
        return result;
    }

    public CityDto saveOrUpdate(CityDto dto){
        City city = DtoTool.convert(dto, City.class);
        return DtoTool.convert(cityRepository.saveAndFlush(city), CityDto.class);
    }

    @Override
    public void deleteById(long id) {
        cityRepository.deleteById(id);
    }

    public City findAndReturnFilledCity(City city) {
        //City city = DtoTool.convert(dto, City.class);
        return cityRepository.findByName(city.getName());
    }
}
