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

@RestController
@RequestMapping("/api/pricings")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping
    public ResponseEntity<List<PricingDto>> getAllPricings(){
        List<PricingDto> pricings = pricingService.getAll();
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingDto> getPricingById(@PathVariable("id") long id){
        PricingDto pricing = pricingService.getById(id);
        return new ResponseEntity<>(pricing, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PricingDto> createPricing(@RequestBody PricingDto pricing){
        PricingDto createdPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(createdPricing, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PricingDto> updatePricing(@PathVariable("id") long id, @RequestBody PricingDto pricing){
        PricingDto updatedPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(updatedPricing, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePricing(@PathVariable("id") long id){
        pricingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pricing with id = " + id + " deleted.");
    }

}
