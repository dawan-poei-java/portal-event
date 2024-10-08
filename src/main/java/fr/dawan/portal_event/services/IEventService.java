package fr.dawan.portal_event.services;

import fr.dawan.portal_event.dto.EventDto;
import java.util.List;

public interface IEventService {

    EventDto saveOrUpdate(EventDto event);
    List<EventDto> getAllEvents();
    EventDto getById(long id);
    void deleteEvent(long id);
    List<EventDto> getUpcomingEvents();
    List<EventDto> getEventsByCity(String cityName);
    EventDto getEventByIdAndCity(long id, String cityName);
    
}
