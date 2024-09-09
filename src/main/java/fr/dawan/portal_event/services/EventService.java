package fr.dawan.portal_event.services;

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

}
