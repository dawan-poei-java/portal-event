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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pricings")
@Tag(name = "Pricing", description = "API de gestion des tarifs")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @Operation(
        summary = "Obtenir tous les tarifs", 
        description = "Récupère la liste de tous les tarifs disponibles"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Liste des tarifs récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PricingDto.class))
            )
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<PricingDto>> getAllPricings(){
        List<PricingDto> pricings = pricingService.getAll();
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @Operation(
        summary = "Obtenir un tarif par ID", 
        description = "Récupère un tarif spécifique par son identifiant"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarif récupéré avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PricingDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Tarif non trouvé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PricingDto> getPricingById(@PathVariable("id") long id){
        PricingDto pricing = pricingService.getById(id);
        return new ResponseEntity<>(pricing, HttpStatus.OK);
    }

    @Operation(
        summary = "Créer un nouveau tarif", 
        description = "Crée un nouveau tarif (nécessite le rôle ORGANIZER ou ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Tarif créé avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PricingDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Données d'entrée invalides",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Accès refusé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    public ResponseEntity<PricingDto> createPricing(@RequestBody PricingDto pricing){
        PricingDto createdPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(createdPricing, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Mettre à jour un tarif", 
        description = "Met à jour un tarif existant par son ID (nécessite le rôle ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarif mis à jour avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PricingDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Données d'entrée invalides",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Accès refusé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Tarif non trouvé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<PricingDto> updatePricing(@PathVariable("id") long id, @RequestBody PricingDto pricing){
        PricingDto updatedPricing = pricingService.saveOrUpdate(pricing);
        return new ResponseEntity<>(updatedPricing, HttpStatus.OK);
    }

    @Operation(
        summary = "Supprimer un tarif", 
        description = "Supprime un tarif par son ID (nécessite le rôle ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarif supprimé avec succès",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(type = "string")
            )
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Accès refusé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Tarif non trouvé",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erreur interne du serveur",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<String> deletePricing(@PathVariable("id") long id){
        pricingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pricing with id = " + id + " deleted.");
    }

}