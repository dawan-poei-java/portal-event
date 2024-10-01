package fr.dawan.portal_event.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CustomUserDetails;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.repositories.UserRepository;

@Service
public class JwtService {


    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    @Autowired
    private UserRepository userRepository;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();

/*         // Récupérer les rôles/autorités de l'utilisateur
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

                        // Récupérer le userId
        Long userId = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof CustomUserDetails) {
                userId = ((CustomUserDetails) userDetails).getUserId();
            }
        } */

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getFirstName() + " " + user.getLastName())
                .claim("roles", user.getUserRole())  // Ajouter les rôles comme une réclamation
                .claim("user_id", user.getId())
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }


    public User getUserFromJwt(Jwt token) {
        try {
            // Extraire l'ID de l'utilisateur directement du token Jwt
            Long userId = ((Number) token.getClaim("user_id")).longValue();

            // Rechercher l'utilisateur dans la base de données
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new RuntimeException("Utilisateur non trouvé pour le token fourni");
            }
        } catch (Exception exception) {
            throw new RuntimeException("Token JWT invalide", exception);
        }
    }

}
