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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cities")
@Tag(name = "City", description = "API pour la gestion des villes")
public class CityController {

    @Autowired
    private CityService cityService;

    @Operation(
        summary = "Obtenir toutes les villes",
        description = "Récupère la liste de toutes les villes"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des villes récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities(){
        List<CityDto> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(
        summary = "Obtenir une ville par ID",
        description = "Récupère une ville spécifique par son ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ville récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Ville non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable("id") long id){
        CityDto city = cityService.getById(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Operation(
        summary = "Créer une nouvelle ville",
        description = "Crée une nouvelle ville (nécessite le rôle ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ville créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Entrée invalide"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto city){
        CityDto createdCity = cityService.saveOrUpdate(city);
        return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Mettre à jour une ville",
        description = "Met à jour une ville existante par son ID (nécessite le rôle ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ville mise à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Entrée invalide"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Ville non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<CityDto> updateCity(@PathVariable("id") long id, @RequestBody CityDto city){
        CityDto updatedCity = cityService.saveOrUpdate(city);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @Operation(
        summary = "Supprimer une ville",
        description = "Supprime une ville par son ID (nécessite le rôle ADMIN)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ville supprimée avec succès"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Ville non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<String> deleteCity(@PathVariable("id") long id){
        cityService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("City with id = " + id + " deleted.");
    }
}
