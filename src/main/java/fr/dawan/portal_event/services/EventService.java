package fr.dawan.portal_event.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.repositories.EventRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class EventService implements IEventService {

    @Autowired
    private EventRepository eventRepository;


    public List<EventDto> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : events){
            eventDtos.add(DtoTool.convert(event, EventDto.class));
        }
        return eventDtos;
    }

    public EventDto getById(long id){
        return DtoTool.convert(eventRepository.findById(id).get(), EventDto.class);
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public EventDto saveOrUpdate(EventDto eventDto) {
        Event event = DtoTool.convert(eventDto, Event.class);
        Event savedEvent = eventRepository.saveAndFlush(event);
        return DtoTool.convert(savedEvent, EventDto.class);
    }

}
