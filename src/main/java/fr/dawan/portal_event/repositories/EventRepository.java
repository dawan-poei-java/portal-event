package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.dto.EventDto;


public interface EventRepository extends JpaRepository<EventDto, Long>{
    //List<Event> findByEventType(EventType eventType);
}
