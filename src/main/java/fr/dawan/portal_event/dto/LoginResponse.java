package fr.dawan.portal_event.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import fr.dawan.portal_event.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    @Schema(description = "JWT Token")
    private String token;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "Role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Schema(description = "First Name")
    private String firstName;
    @Schema(description = "Last Name")
    private String lastName;
    @Schema(description = "Expiration Date")
    private LocalDateTime expiresAt;
}
