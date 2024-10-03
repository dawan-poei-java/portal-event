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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/typeEvents")
@Tag(name = "Type Event", description = "API pour la gestion des types d'événements")
public class TypeEventController {

    @Autowired
    private TypeEventService typeEventService;

    @Operation(summary = "Obtenir tous les types d'événements", description = "Récupère une liste de tous les types d'événements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des types d'événements récupérée avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = TypeEventDto[].class))),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TypeEventDto>> getAllTypeEvents(){
        List<TypeEventDto> typeEvents = typeEventService.getAll();
        return new ResponseEntity<>(typeEvents, HttpStatus.OK);
    }

    @Operation(summary = "Obtenir un type d'événement par ID", description = "Récupère un type d'événement spécifique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Type d'événement récupéré avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = TypeEventDto.class))),
        @ApiResponse(responseCode = "404", description = "Type d'événement non trouvé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TypeEventDto> getTypeEventById(@PathVariable long id){
        TypeEventDto typeEvent = typeEventService.getById(id);
        return new ResponseEntity<>(typeEvent, HttpStatus.OK);
    }

    @Operation(summary = "Créer un nouveau type d'événement", description = "Crée un nouveau type d'événement (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Type d'événement créé avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = TypeEventDto.class))),
        @ApiResponse(responseCode = "400", description = "Entrée invalide",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<TypeEventDto> createTypeEvent(@RequestBody TypeEventDto typeEvent){
        TypeEventDto createdTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(createdTypeEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un type d'événement", description = "Met à jour un type d'événement existant par son ID (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Type d'événement mis à jour avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = TypeEventDto.class))),
        @ApiResponse(responseCode = "400", description = "Entrée invalide",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Type d'événement non trouvé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<TypeEventDto> updateTypeEvent(@PathVariable("id") long id, @RequestBody TypeEventDto typeEvent){
        TypeEventDto updatedTypeEvent = typeEventService.saveOrUpdate(typeEvent);
        return new ResponseEntity<>(updatedTypeEvent, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un type d'événement", description = "Supprime un type d'événement par son ID (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Type d'événement supprimé avec succès",
                     content = @Content(mediaType = "text/plain",
                     schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Type d'événement non trouvé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<String> deleteTypeEvent(@PathVariable("id") long id){
        typeEventService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("TypeEvent with id = " + id + " deleted.");
    }
}
