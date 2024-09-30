package fr.dawan.portal_event.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;


public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT e FROM Event e WHERE e.startDate > CURRENT_TIMESTAMP ORDER BY e.startDate ASC LIMIT 8")
    List<Event> findUpcomingEvents();
    
    @Query("SELECT e FROM Event e WHERE e.city.id = :cityId")
    List<Event> findAllByCityId(Long cityId);
    //List<Event> findByEventType(EventType eventType);
}
