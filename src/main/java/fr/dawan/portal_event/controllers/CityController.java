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

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Operation(summary = "Get all cities", description = "Retrieve a list of all cities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of cities"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities(){
        List<CityDto> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Get a city by ID", description = "Retrieve a specific city by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the city"),
        @ApiResponse(responseCode = "404", description = "City not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable("id") long id){
        CityDto city = cityService.getById(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Operation(summary = "Create a new city", description = "Create a new city (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the city"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto city){
        CityDto createdCity = cityService.saveOrUpdate(city);
        return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a city", description = "Update an existing city by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the city"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "City not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<CityDto> updateCity(@PathVariable("id") long id, @RequestBody CityDto city){
        CityDto updatedCity = cityService.saveOrUpdate(city);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @Operation(summary = "Delete a city", description = "Delete a city by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the city"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "City not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<String> deleteCity(@PathVariable("id") long id){
        cityService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("City with id = " + id + " deleted.");
    }
}
