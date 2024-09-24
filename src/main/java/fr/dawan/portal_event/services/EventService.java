package fr.dawan.portal_event.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public Event createEvent(Event event) {
        return eventRepository.save(event);  // Sauvegarde l'événement en base de données
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getById(long id){
        return eventRepository.findById(id).get();
    }

}
