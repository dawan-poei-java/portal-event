package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.TypeEventDto;

public interface ITypeEventService {
    List<TypeEventDto> getAll();
    TypeEventDto getById(long id);
    TypeEventDto saveOrUpdate(TypeEventDto dto);
    void deleteById(long id);
}
