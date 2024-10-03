package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.CustomUserDetails;
import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.services.AuthenticationService;
import fr.dawan.portal_event.services.JwtService;
import fr.dawan.portal_event.services.UserService;
import fr.dawan.portal_event.utils.DtoTool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController // Déclare cette classe comme un contrôleur REST, gérant les requêtes HTTP
@RequestMapping("/api/users") // Indique que toutes les routes dans cette classe sont préfixées par /api/users
public class UserController {

    @Autowired
    private UserService userService; // Injection du service UserService pour gérer les utilisateurs

    @Autowired
    private JwtService jwtService; // Injection du service JwtService pour gérer les jetons JWT

    @Autowired
    private AuthenticationService authenticationService; // Service d'authentification pour vérifier les droits des utilisateurs

    // Méthode pour récupérer tous les utilisateurs
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "", produces = "application/json") // Route pour récupérer tous les utilisateurs en JSON
    @PreAuthorize("hasRole('ADMIN')") // Autorisation : seuls les administrateurs peuvent accéder
    public ResponseEntity<List<UserDto>> getAllUsers() throws Exception {
        List<UserDto> users = userService.getAllBy(0, 0, ""); // Appel au service pour récupérer les utilisateurs
        return new ResponseEntity<>(users, HttpStatus.OK); // Retourne la liste avec un statut HTTP 200
    }

    // Méthode pour récupérer un utilisateur spécifique par son ID
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = "application/json") // Route pour récupérer un utilisateur par son ID
    @PreAuthorize("hasRole('ADMIN') or @authenticationService.isAuthenticatedUserMatchingId(#id)") // Autorisation : administrateurs ou utilisateur correspondant à l'ID
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception {
        UserDto dto = userService.getById(id); // Récupère l'utilisateur via son ID
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id + " not found."); // Si l'utilisateur n'existe pas, retourne 404
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(dto); // Retourne l'utilisateur avec un statut 200
        }
    }

    // Méthode pour mettre à jour un utilisateur existant
    @Operation(summary = "Update an existing user", description = "Update an existing user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the user", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json") // Route pour mettre à jour un utilisateur par son ID
    @PreAuthorize("hasRole('ADMIN') or @authenticationService.isAuthenticatedUserMatchingId(#id)") // Autorisation : administrateurs ou utilisateur correspondant à l'ID
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, @RequestBody UserDto dto) throws Exception {
        dto.setId(id); // Associe l'ID de l'utilisateur au DTO
        UserDto updatedUser = userService.saveOrUpdate(dto); // Sauvegarde ou met à jour l'utilisateur
        if (updatedUser == null) {
            return ResponseEntity.notFound().build(); // Si l'utilisateur n'existe pas, retourne 404
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK); // Retourne l'utilisateur mis à jour avec un statut 200
    }

    // Méthode pour supprimer un utilisateur par son ID
    @Operation(summary = "Delete a user", description = "Delete a user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the user", 
                     content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "404", description = "User not found", 
                     content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(value = "/{id}", produces = "text/plain") // Route pour supprimer un utilisateur par son ID
    @PreAuthorize("hasRole('ADMIN')") // Autorisation : seuls les administrateurs peuvent supprimer des utilisateurs
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws Exception {
        UserDto dto = userService.getById(id); // Récupère l'utilisateur par son ID
        if (dto != null) {
            userService.deleteById(id); // Supprime l'utilisateur si trouvé
            return ResponseEntity.status(HttpStatus.OK).body("User with id = " + id + " deleted."); // Retourne un message de suppression
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id + " is not found."); // Si l'utilisateur n'est pas trouvé, retourne 404
        }
    }

    // Méthode pour récupérer les données de l'utilisateur actuellement authentifié
    @GetMapping("/me") // Route pour récupérer les informations de l'utilisateur authentifié
    @PreAuthorize("isAuthenticated()") // Autorisation : utilisateur doit être authentifié
    public ResponseEntity<UserDto> getAuthentifiedUserData() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Récupère l'objet d'authentification actuel
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authenticate : " + authentication.getPrincipal()); // Affiche les détails de l'utilisateur authentifié
            if (authentication.getPrincipal() instanceof Jwt) { // Vérifie si le principal est un objet Jwt
                Jwt jwt = (Jwt) authentication.getPrincipal(); // Récupère le jeton JWT
                UserDto userDto = DtoTool.convert(jwtService.getUserFromJwt(jwt), UserDto.class); // Convertit le JWT en UserDto
                if (userDto != null) {
                    return ResponseEntity.ok(userDto); // Retourne les informations de l'utilisateur
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Si l'utilisateur n'est pas authentifié, retourne 401
    }
}

