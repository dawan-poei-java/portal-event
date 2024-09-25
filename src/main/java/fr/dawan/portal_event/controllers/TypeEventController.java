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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/typeEvents")
public class TypeEventController {

    @Autowired
    private TypeEventService typeEventService;

    @Operation(summary = "Get all type events", description = "Retrieve a list of all type events")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of type events"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TypeEventDto>> getAllTypeEvents(){
        List<TypeEventDto> typeEvents = typeEventService.getAll();
        return new ResponseEntity<>(typeEvents, HttpStatus.OK);
    }

    @Operation(summary = "Get a type event by ID", description = "Retrieve a specific type event by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the type event"),
        @ApiResponse(responseCode = "404", description = "Type event not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TypeEventDto> getTypeEventById(@PathVariable long id){
        TypeEventDto typeEvent = typeEventService.getById(id);
        return new ResponseEntity<>(typeEvent, HttpStatus.OK);
    }

    @Operation(summary = "Create a new type event", description = "Create a new type event (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the type event"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> createTypeEvent(@RequestBody TypeEventDto typeEvent){
        TypeEventDto createdTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(createdTypeEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a type event", description = "Update an existing type event by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the type event"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Type event not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeEventDto> updateTypeEvent(@PathVariable("id") long id, @RequestBody TypeEventDto typeEvent){
        TypeEventDto updatedTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(updatedTypeEvent, HttpStatus.OK);
    }

    @Operation(summary = "Delete a type event", description = "Delete a type event by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the type event"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Type event not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteTypeEvent(@PathVariable("id") long id){
        typeEventService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("TypeEvent with id = " + id + " deleted.");
    }
}
