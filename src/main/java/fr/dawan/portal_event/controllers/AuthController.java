package fr.dawan.portal_event.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.services.JwtService;

@RestController
public class AuthController {

    private JwtService jwtService;

    public AuthController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String getToken(Authentication authentication){
        String token = jwtService.generateToken(authentication);
        return token;
    }
}
