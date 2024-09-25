package fr.dawan.portal_event.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.repositories.EventRepository;

@Service
public class EventService implements IEventService {

    @Autowired
    private EventRepository eventRepository;


    public List<EventDto> getAllEvents(){
        return eventRepository.findAll();
    }

    public EventDto getById(long id){
        return eventRepository.findById(id).get();
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public EventDto saveOrUpdate(EventDto eventDto) {
        return eventRepository.saveAndFlush(eventDto);
    }

}
