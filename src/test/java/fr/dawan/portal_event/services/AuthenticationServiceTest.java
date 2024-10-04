package fr.dawan.portal_event.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.dto.CustomUserDetails;
import fr.dawan.portal_event.dto.LoginResponse;
import fr.dawan.portal_event.dto.UserRequestDto;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.repositories.UserRepository;
import jakarta.security.auth.message.AuthException;

import java.util.Optional;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() throws AuthException{
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserRole(UserRole.USER);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication authResult = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authResult);

        when(jwtService.generateToken(user)).thenReturn("mockedToken");

        LoginResponse response = authenticationService.authenticate(email, password);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void testRegisterNewUser() {
        // Implémentation du test
    }


    // Autres méthodes de test...
}
