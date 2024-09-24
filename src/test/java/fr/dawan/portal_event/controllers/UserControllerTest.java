package fr.dawan.portal_event.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.services.JwtService;
import fr.dawan.portal_event.services.UserService;

@WebMvcTest(UserController.class)
@ActiveProfiles("TEST")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private UserDto userDto;

    private List<UserDto> users;

    private String token;

    @BeforeEach
    public void setup() {
        //userDto = new UserDto(1L, "John", "Doe", "2vqkA@example.com", "123456789", "123 Main Street", "Apt. 456", "Test City", "12345", "password", LocalDateTime.now(), UserRole.ORGANIZER);

        users = new ArrayList<UserDto>();
        users.add(userDto);

        // Simulate authentication
        when(userService.saveOrUpdate(any(UserDto.class))).thenReturn(userDto);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        token = jwtService.generateToken(authentication);    
        
    }

    @AfterEach
    public void tearDown() {
        userService.deleteById(1L);
    }

    //TODO: vérifier pourquoi l'authentification ne fonctionne pas, on a besoin d'un Token pour les requêtes
    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllBy(0, 0, "")).thenReturn(users);
        MockHttpServletResponse response = mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        //JSONAssert.assertEquals("");
    }

    @Test
    public void testGetUserById() throws Exception {
        // TODO: Implement test cases
    }

    @Test
    public void testCreateUser() throws Exception {
        // TODO: Implement test cases
    }

    @Test
    public void testUpdateUser() throws Exception {
        // TODO: Implement test cases
    }

    @Test
    public void testDeleteUser() throws Exception {
        // TODO: Implement test cases
    }


}
