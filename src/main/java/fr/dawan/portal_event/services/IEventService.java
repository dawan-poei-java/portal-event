package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.EventDto;

public interface IEventService {
    List<EventDto> getAllBy(int page, int size, String search);
    EventDto saveOrUpdate(EventDto dto);
    void deleteById(long id);
    EventDto getById(long id);

}
