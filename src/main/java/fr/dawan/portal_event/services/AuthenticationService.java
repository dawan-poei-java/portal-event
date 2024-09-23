package fr.dawan.portal_event.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CustomUserDetails;
import fr.dawan.portal_event.dto.LoginResponse;
import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.repositories.UserRepository;
import fr.dawan.portal_event.utils.DtoTool;
import jakarta.security.auth.message.AuthException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(String email, String password) throws AuthException{
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Object principal = authentication.getPrincipal();
        if(principal instanceof CustomUserDetails){
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            LocalDateTime now = LocalDateTime.now();
            LoginResponse response = new LoginResponse(
                jwtService.generateToken(authentication),
                userDetails.getUsername(),
                userDetails.getRole(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                now.plusDays(1)
            );
            return response;
            
        }
        else throw new AuthException("Authentication failed");
    }

    public LoginResponse register(UserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Cet utilisateur existe déjà !");
        }

        dto.setRole(dto.getRole() == null ? UserRole.USER : dto.getRole());
        User user = DtoTool.convert(dto, User.class);

        userRepository.save(user);

        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + dto.getRole()));

        LoginResponse response = new LoginResponse(
            jwtService.generateToken(new UsernamePasswordAuthenticationToken(dto.getEmail(), null, authorities)),
            dto.getEmail(),
            dto.getRole(),
            dto.getFirstName(),
            dto.getLastName(),
            LocalDateTime.now().plusDays(1)
        );

        return response;
    }
}
