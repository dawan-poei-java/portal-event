package fr.dawan.portal_event.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import fr.dawan.portal_event.enums.UserRole;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    private String firstName;
    private String lastName;

    private LocalDateTime expiresAt;
}
