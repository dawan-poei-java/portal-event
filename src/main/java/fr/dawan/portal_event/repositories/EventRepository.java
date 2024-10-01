package fr.dawan.portal_event.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;
import org.springframework.data.repository.query.Param;


public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT e FROM Event e WHERE e.startDate > CURRENT_TIMESTAMP ORDER BY e.startDate ASC LIMIT 8")
    List<Event> findUpcomingEvents();
    
    @Query("SELECT e FROM Event e WHERE e.city.id = :cityId")
    List<Event> findAllByCityId(Long cityId);
    //List<Event> findByEventType(EventType eventType);


    @Query("SELECT e FROM Event e LEFT JOIN e.reservations r WHERE e.startDate > CURRENT_TIMESTAMP GROUP BY e ORDER BY COUNT(r) DESC LIMIT 1")
    Event findMostPopularEvent();

    @Query("SELECT e FROM Event e WHERE e.id = :id AND e.city.id = :cityId")
    Event findEventByIdAndCityName(Long id, Long cityId);

}
