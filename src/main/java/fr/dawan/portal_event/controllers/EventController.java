package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.services.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<List<EventDto>> getAllEvents() throws Exception{
            List<EventDto> events = eventService.getAllBy(0,0,"");
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception{
        EventDto dto = eventService.getById(id);
        if(dto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with id = " + id +" not found.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @PostMapping(value="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EventDto> saveOrUpdate(@RequestBody EventDto dto) throws Exception{
        EventDto createdEvent = eventService.saveOrUpdate(dto);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{id}", produces = "text/plain")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws Exception{
        EventDto dto = eventService.getById(id);
        if(dto!=null){
            eventService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Event with id = " + id + " deleted.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with id = " + id + " is not found.");
        }
    }
}
