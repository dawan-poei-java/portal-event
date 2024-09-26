package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.TypeEventDto;
import fr.dawan.portal_event.entities.TypeEvent;
import fr.dawan.portal_event.repositories.TypeEventRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class TypeEventService implements ITypeEventService {

    @Autowired
    private TypeEventRepository typeEventRepository;

    @Override
    public List<TypeEventDto> getAll() {
        List<TypeEvent> typeEvents = typeEventRepository.findAll();
        List<TypeEventDto> result = new ArrayList<TypeEventDto>();
        for(TypeEvent typeEvent: typeEvents){
            TypeEventDto dto = DtoTool.convert(typeEvent, TypeEventDto.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public TypeEventDto getById(long id) {
        return DtoTool.convert(typeEventRepository.findById(id).get(), TypeEventDto.class);
    }

    @Override
    public TypeEventDto saveOrUpdate(TypeEventDto dto) {
        TypeEvent typeEvent = DtoTool.convert(dto, TypeEvent.class);
        return DtoTool.convert(typeEventRepository.saveAndFlush(typeEvent), TypeEventDto.class);
    }

    @Override
    public void deleteById(long id) {
        typeEventRepository.deleteById(id);
    }

}
