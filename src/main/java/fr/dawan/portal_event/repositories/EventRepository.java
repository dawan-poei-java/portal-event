package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;


public interface EventRepository extends JpaRepository<Event, Long>{
    //List<Event> findByEventType(EventType eventType);
}
