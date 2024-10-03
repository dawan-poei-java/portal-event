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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "API pour la gestion des utilisateurs")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère une liste de tous les utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping(value="", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<List<UserDto>> getAllUsers() throws Exception {
        List<UserDto> users = userService.getAllBy(0, 0, "");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtenir un utilisateur par ID", description = "Récupère un utilisateur spécifique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                     content = @Content(mediaType = "text/plain",
                                        schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping(value="/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or @authenticationService.isAuthenticatedUserMatchingId(#id)")    
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception{
        UserDto dto = userService.getById(id);
        if(dto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id +" not found.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @Operation(summary = "Mettre à jour un utilisateur existant", description = "Met à jour un utilisateur existant par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Entrée invalide",
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @PutMapping(value="/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or @authenticationService.isAuthenticatedUserMatchingId(#id)")    
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, @RequestBody UserDto dto) throws Exception {
        dto.setId(id);
        UserDto updatedUser = userService.saveOrUpdate(dto);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès",
                     content = @Content(mediaType = "text/plain",
                                        schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "403", description = "Accès refusé",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                     content = @Content(mediaType = "text/plain",
                                        schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @DeleteMapping(value="/{id}", produces = "text/plain")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws Exception{
        UserDto dto = userService.getById(id);
        if(dto!=null) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with id = " + id + " deleted.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id + " is not found.");
        }
    }

    @Operation(summary = "Obtenir les données de l'utilisateur authentifié", description = "Récupère les données de l'utilisateur actuellement authentifié")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Données de l'utilisateur récupérées avec succès",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "401", description = "Non autorisé",
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                     content = @Content)
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getAuthentifiedUserData() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authenticate : " + authentication.getPrincipal());
            // Vérifiez si le principal est un objet Jwt
            if (authentication.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                // Utilisez le jeton JWT pour obtenir les informations de l'utilisateur
                UserDto userDto = DtoTool.convert(jwtService.getUserFromJwt(jwt), UserDto.class);
                
                if (userDto != null) {
                    return ResponseEntity.ok(userDto);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}