package fr.dawan.portal_event.controllers;

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

    /*public AuthController(JwtService jwtService, UserService userService, AuthenticationManager authenticationManager){
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }*/

    @Operation(summary = "Login a user", description = "Login a user and return a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated(OnLogin.class) @RequestBody UserRequestDto dto) throws Exception{
        LoginResponse response = authenticationService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(response);
    }


    // TODO: configuer la route pour retourner des erreurs + précises (User already exists, Invalid input, etc)
    @Operation(summary = "Register a user", description = "Register a user and return a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(OnRegister.class) @RequestBody UserRequestDto dto){
        try {
            City city = cityService.findAndReturnFilledCity(dto.getCity());
            dto.setCity(city);
            UserDto user = new UserDto(dto);
            //user.setCity(DtoTool.convert(cityDto, City.class));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            LoginResponse response = authenticationService.register(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
