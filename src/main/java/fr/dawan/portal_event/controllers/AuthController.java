package fr.dawan.portal_event.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.LoginResponse;
import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.services.AuthenticationService;
import fr.dawan.portal_event.services.JwtService;
import fr.dawan.portal_event.services.UserService;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*@Autowired
    private AuthenticationManager authenticationManager;*/

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    /*public AuthController(JwtService jwtService, UserService userService, AuthenticationManager authenticationManager){
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto) throws Exception{
        LoginResponse response = authenticationService.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(response);
    }


    // TODO: configuer la route pour retourner des erreurs + précises (User already exists, Invalid input, etc)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto dto){
        try {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            LoginResponse response = userService.register(dto);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
