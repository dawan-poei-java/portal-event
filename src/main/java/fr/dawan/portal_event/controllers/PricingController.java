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

import fr.dawan.portal_event.dto.PricingDto;
import fr.dawan.portal_event.services.PricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/pricings")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @Operation(summary = "Get all pricings", description = "Retrieve a list of all pricings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pricings"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PricingDto>> getAllPricings(){
        List<PricingDto> pricings = pricingService.getAll();
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @Operation(summary = "Get a pricing by ID", description = "Retrieve a specific pricing by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the pricing"),
        @ApiResponse(responseCode = "404", description = "Pricing not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PricingDto> getPricingById(@PathVariable("id") long id){
        PricingDto pricing = pricingService.getById(id);
        return new ResponseEntity<>(pricing, HttpStatus.OK);
    }

    @Operation(summary = "Create a new pricing", description = "Create a new pricing (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the pricing"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PricingDto> createPricing(@RequestBody PricingDto pricing){
        PricingDto createdPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(createdPricing, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a pricing", description = "Update an existing pricing by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the pricing"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Pricing not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<PricingDto> updatePricing(@PathVariable("id") long id, @RequestBody PricingDto pricing){
        PricingDto updatedPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(updatedPricing, HttpStatus.OK);
    }

    @Operation(summary = "Delete a pricing", description = "Delete a pricing by its ID (requires ORGANIZER or ADMIN role)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the pricing"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Pricing not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<String> deletePricing(@PathVariable("id") long id){
        pricingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pricing with id = " + id + " deleted.");
    }

}
