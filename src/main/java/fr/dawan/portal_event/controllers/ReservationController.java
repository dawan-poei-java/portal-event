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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Get all reservations", description = "Retrieves a list of all reservations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ReservationDto.class))),
        @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAll(){
        List<ReservationDto> reservations = reservationService.getAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Operation(summary = "Get a reservation by ID", description = "Returns a single reservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ReservationDto.class))),
        @ApiResponse(responseCode = "404", description = "Reservation not found",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getById(@PathVariable long id){
        ReservationDto reservation = reservationService.getById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
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
