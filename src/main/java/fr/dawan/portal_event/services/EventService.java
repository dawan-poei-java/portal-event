package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.repositories.EventRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class EventService implements IEventService{

    @Autowired
    private EventRepository eventRepository;


    /*public Event createEvent(Event event) {
        return eventRepository.save(event);  // Sauvegarde l'événement en base de données
    }*/

    /*public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }*/

    @Override
    public List<EventDto> getAllBy(int page, int size, String search) {

        List<EventDto> result = new ArrayList<>();
        List<Event> entities = eventRepository.findAll();
        for(Event event: entities){
            EventDto dto = DtoTool.convert(event,EventDto.class);
            result.add(dto);
        }

        return result;
    }


    @Override
    public EventDto saveOrUpdate(EventDto dto) {
        Event event = DtoTool.convert(dto, Event.class);

        Event savedEvent = eventRepository.saveAndFlush(event);
        EventDto savedDto = DtoTool.convert(savedEvent,EventDto.class);

        return savedDto;

    }


    @Override
    public void deleteById(long id) {
        eventRepository.deleteById(id);
    }


    @Override
    public EventDto getById(long id) {
        Optional<Event> optional = eventRepository.findById(id);
        if(optional.isPresent()){
            Event event = optional.get();
            return DtoTool.convert(event,EventDto.class);
        }
        return null;
    }

    

}
