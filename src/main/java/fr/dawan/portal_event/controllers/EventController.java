package fr.dawan.portal_event.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;


        /*
         .d8888b.  888     888  .d8888b. 88888888888 .d88888b.  888b     d888      8888888b.  8888888888 .d88888b.  888     888 8888888888 .d8888b. 88888888888 .d8888b.  
        d88P  Y88b 888     888 d88P  Y88b    888    d88P" "Y88b 8888b   d8888      888   Y88b 888       d88P" "Y88b 888     888 888       d88P  Y88b    888    d88P  Y88b 
        888    888 888     888 Y88b.         888    888     888 88888b.d88888      888    888 888       888     888 888     888 888       Y88b.         888    Y88b.      
        888        888     888  "Y888b.      888    888     888 888Y88888P888      888   d88P 8888888   888     888 888     888 8888888    "Y888b.      888     "Y888b.   
        888        888     888     "Y88b.    888    888     888 888 Y888P 888      8888888P"  888       888     888 888     888 888           "Y88b.    888        "Y88b. 
        888    888 888     888       "888    888    888     888 888  Y8P  888      888 T88b   888       888 Y8b 888 888     888 888             "888    888          "888 
        Y88b  d88P Y88b. .d88P Y88b  d88P    888    Y88b. .d88P 888   "   888      888  T88b  888       Y88b.Y8b88P Y88b. .d88P 888       Y88b  d88P    888    Y88b  d88P 
        "Y8888P"   "Y88888P"   "Y8888P"     888     "Y88888P"  888       888      888   T88b 8888888888 "Y888888"   "Y88888P"  8888888888 "Y8888P"     888     "Y8888P"                                                                                                                                                                                                                                                                                                                 
    */
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDto>> getUpcomingEvents(){
        List<EventDto> events = eventService.getUpcomingEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Récupérer les events pour une ville en particulier
    @GetMapping(value = "/city/{cityName}")
    public ResponseEntity<List<EventDto>> getEventsByCity(@PathVariable("cityName") String cityName){
        List<EventDto> events = eventService.getEventsByCity(cityName);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    @GetMapping(value = "/popular")
    public ResponseEntity<EventDto> getPopularEvent(){
        EventDto event = eventService.getPopularEvent();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/city/{cityName}/{id}")
    public ResponseEntity<EventDto>getEventByIdAndCity(@PathVariable("cityName") String cityName,@PathVariable("id") Long id){
        EventDto eventDto = eventService.getEventByIdAndCity(id, cityName);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);

    }


    @GetMapping(value = "/organizer")
    public ResponseEntity<List<EventDto>> getEventsByOrganizer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof Jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Jwt jwt = (Jwt) authentication.getPrincipal();
        boolean isOrganizer = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ORGANIZER"));
        System.out.println("Rôle de l'utilisateur : " + authentication.getAuthorities());
        if (!isOrganizer) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        
        Long organizerId = jwt.getClaim("user_id");
        if (organizerId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<EventDto> events= eventService.getEventsByOrganizer(organizerId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    /*
    888888b.         d8888  .d8888b. 8888888 .d8888b.        .d8888b.  8888888b.  888     888 8888888b.  
    888  "88b       d88888 d88P  Y88b  888  d88P  Y88b      d88P  Y88b 888   Y88b 888     888 888  "Y88b 
    888  .88P      d88P888 Y88b.       888  888    888      888    888 888    888 888     888 888    888 
    8888888K.     d88P 888  "Y888b.    888  888             888        888   d88P 888     888 888    888 
    888  "Y88b   d88P  888     "Y88b.  888  888             888        8888888P"  888     888 888    888 
    888    888  d88P   888       "888  888  888    888      888    888 888 T88b   888     888 888    888 
    888   d88P d8888888888 Y88b  d88P  888  Y88b  d88P      Y88b  d88P 888  T88b  Y88b. .d88P 888  .d88P 
    8888888P" d88P     888  "Y8888P" 8888888 "Y8888P"        "Y8888P"  888   T88b  "Y88888P"  8888888P"  
     */

    @Operation(summary = "Get all events", description = "Retrieve a list of all events")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of events",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @GetMapping
    //@PreAuthorize("isAuthenticated")
    public ResponseEntity<List<EventDto>> getAllEvents(){
        List<EventDto> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @Operation(summary = "Get an event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the event",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "404", description = "Event not found",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @GetMapping("/{id:\\d+}") // Test pour accepter seulement les id en format numérique
    public ResponseEntity<EventDto> getEventById(@PathVariable("id") long id){
        EventDto event = eventService.getById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @Operation(summary = "Create a new event", description = "Create a new event (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the event",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Access denied",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ORGANIZER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EventDto> createEvent(@RequestPart("event") EventDto event,
                                                @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                HttpServletRequest request) throws IOException {
        System.out.println("TypeEvent de l'évènement : " + event.getTypeEvent().getName());
        System.out.println("City de l'évènement : " + event.getCity().getName());

        // Gestion des images et des URLs
        List<String> imageUrls = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    String imageUrl = baseUrl + "/uploads/" + fileName;
                    imageUrls.add(imageUrl);
                }
            }
        }
        
        event.setImages(imageUrls);
        EventDto createdEvent = eventService.saveOrUpdate(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an event", description = "Update an existing event (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the event",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Access denied",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Event not found",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("id") long id, @RequestBody EventDto event){
        event.setId(id);
        EventDto updatedEvent = eventService.saveOrUpdate(event);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @Operation(summary = "Delete an event", description = "Delete an existing event (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the event",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Access denied",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Event not found",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") long id){
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
