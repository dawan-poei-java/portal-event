package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.dto.ErrorResponse;
import fr.dawan.portal_event.dto.LoginResponse;
import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.dto.UserRequestDto;
import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.services.AuthenticationService;
import fr.dawan.portal_event.services.CityService;
import fr.dawan.portal_event.utils.DtoTool;
import fr.dawan.portal_event.validations.OnLogin;
import fr.dawan.portal_event.validations.OnRegister;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping(value = "/api")
public class AuthController {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*@Autowired
    private AuthenticationManager authenticationManager;*/

    private final AuthenticationService authenticationService;

    @Autowired
    private CityService cityService;

    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Login a user", description = "Login a user and return a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Entrée invalide", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentification échouée", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated(OnLogin.class) @RequestBody UserRequestDto dto) throws Exception {
        LoginResponse response = authenticationService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Register a user", description = "Register a user and return a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(OnRegister.class) @RequestBody UserRequestDto dto) {
        try {
            System.out.println("DTO reçu : " + dto.toString());
            CityDto cityDto = cityService.getById(dto.getCity().getId());
            dto.setCity(DtoTool.convert(cityDto,City.class));
            UserDto user = new UserDto(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("UserDto : " + user.toString());
            LoginResponse response = authenticationService.register(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Registration error", HttpStatus.BAD_REQUEST.value(), List.of(e.getMessage())));
        }
    }
}

