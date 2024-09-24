package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.TypeEventDto;
import fr.dawan.portal_event.services.TypeEventService;

@RestController
@RequestMapping("/api/typeEvents")
public class TypeEventController {

    @Autowired
    private TypeEventService typeEventService;

    @GetMapping
    public ResponseEntity<List<TypeEventDto>> getAllTypeEvents(){
        List<TypeEventDto> typeEvents = typeEventService.getAll();
        return new ResponseEntity<>(typeEvents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeEventDto> getTypeEventById(@PathVariable long id){
        TypeEventDto typeEvent = typeEventService.getById(id);
        return new ResponseEntity<>(typeEvent, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> createTypeEvent(@RequestBody TypeEventDto typeEvent){
        TypeEventDto createdTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(createdTypeEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> updateTypeEvent(@PathVariable("id") long id, @RequestBody TypeEventDto typeEvent){
        TypeEventDto updatedTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(updatedTypeEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteTypeEvent(@PathVariable("id") long id){
        typeEventService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("TypeEvent with id = " + id + " deleted.");
    }
}
