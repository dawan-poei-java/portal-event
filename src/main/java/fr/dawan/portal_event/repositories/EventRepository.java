package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.Event;
import java.util.List;
import javax.swing.event.DocumentEvent.EventType;


public interface EventRepository extends JpaRepository<Event, Long>{
    //List<Event> findByEventType(EventType eventType);
}
