package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.dawan.portal_event.dto.ReservationDto;
import fr.dawan.portal_event.services.ReservationService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Réservations", description = "API de gestion des réservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Operation(
        summary = "Obtenir toutes les réservations",
        description = "Récupère une liste de toutes les réservations"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ReservationDto.class))
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Accès non autorisé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAll(){
        List<ReservationDto> reservations = reservationService.getAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(
        summary = "Obtenir une réservation par ID",
        description = "Renvoie une seule réservation"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Réservation récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReservationDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Réservation non trouvée",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Accès non autorisé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getById(@PathVariable long id){
        ReservationDto reservation = reservationService.getById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @Operation(
        summary = "Obtenir les réservations d'un utilisateur",
        description = "Récupère une liste de toutes les réservations pour un utilisateur donné"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ReservationDto.class))
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Accès non autorisé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDto> reservationDtos = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }
    
    @Operation(summary = "Create a new reservation", description = "Creates a new reservation and returns the created entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reservation successfully created",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ReservationDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto){
        ReservationDto createdReservation = reservationService.saveOrUpdate(reservationDto);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing reservation", description = "Updates a reservation and returns the updated entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation successfully updated",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ReservationDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Reservation not found",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable long id, @RequestBody ReservationDto reservationDto){
        ReservationDto updatedReservation = reservationService.saveOrUpdate(reservationDto);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @Operation(summary = "Delete a reservation", description = "Deletes a reservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reservation successfully deleted",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Reservation not found",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id){
        reservationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
