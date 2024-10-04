package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.CategoryDto;
import fr.dawan.portal_event.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Catégorie", description = "API de gestion des catégories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Obtenir toutes les catégories", description = "Récupère la liste de toutes les catégories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des catégories récupérée avec succès",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = categoryService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Obtenir une catégorie par ID", description = "Récupère une catégorie spécifique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Catégorie récupérée avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Catégorie non trouvée",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@Parameter(description = "ID de la catégorie", required = true) @PathVariable("id") long id){
        CategoryDto category = categoryService.getById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(summary = "Créer une nouvelle catégorie", description = "Crée une nouvelle catégorie (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Catégorie créée avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "400", description = "Entrée invalide",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category){
        CategoryDto createdCategory = categoryService.saveOrUpdate(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour une catégorie", description = "Mise à jour d'une catégorie existante par son ID (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Catégorie mise à jour avec succès",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "400", description = "Entrée invalide",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Catégorie non trouvée",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") long id, @RequestBody CategoryDto category){
        CategoryDto updatedCategory = categoryService.saveOrUpdate(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer une catégorie", description = "Suppression d'une catégorie par son ID (nécessite le rôle ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Catégorie supprimée avec succès",
                     content = @Content(mediaType = "text/plain",
                     schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Catégorie non trouvée",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
