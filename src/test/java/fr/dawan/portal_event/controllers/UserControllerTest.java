package fr.dawan.portal_event.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
import static org.junit.jupiter.api.Assertions.fail;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.services.JwtService;
import fr.dawan.portal_event.services.UserService;

@WebMvcTest(UserController.class)
@ActiveProfiles("TEST")
@AutoConfigureMockMvc(addFilters = false) // Disable Spring Security filters for this test
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
        userDto = UserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .build();

        users = new ArrayList<>();
        users.add(userDto);

        // Mock JWT token generation
        token = "mocked_jwt_token";
        //when(jwtService.generateToken(any(Authentication.class))).thenReturn(token);
    }


    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllBy(0, 0, "")).thenReturn(users);

        // Create a mock Authentication object
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("john.doe@example.com", null, authorities);

        // Perform the GET request with authentication
        MvcResult result = mockMvc.perform(get("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.authentication(auth)) // Use Spring Security's test support
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();
        System.out.println("Response content: " + responseContent);
        System.out.println("Response status: " + response.getStatus());
        System.out.println("Response headers: " + response.getHeaderNames());

        // Verify the response
        if (responseContent.isEmpty()) {
            fail("Response content is empty. Status: " + response.getStatus());
        }

        String expectedJson = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\",\"createdAt\":\"" + userDto.getCreatedAt().toString() + "\"}]";
        JSONAssert.assertEquals(expectedJson, responseContent, false);

        verify(userService, times(1)).getAllBy(0, 0, "");
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getById(1L)).thenReturn(userDto);

        // Create a mock Authentication object
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("john.doe@example.com", null, authorities);

        // Perform the GET request with authentication
        MvcResult result = mockMvc.perform(get("/api/users/1")
                .with(SecurityMockMvcRequestPostProcessors.authentication(auth))
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        // Verify the response
        if (responseContent.isEmpty()) {
            fail("Response content is empty. Status: " + response.getStatus());
        }

        String expectedJson = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\",\"createdAt\":\"" + userDto.getCreatedAt().toString() + "\"}";
        JSONAssert.assertEquals(expectedJson, responseContent, false);

        verify(userService, times(1)).getById(1L);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.saveOrUpdate(any(UserDto.class))).thenReturn(userDto);

        // Create a mock Authentication object
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("john.doe@example.com", null, authorities);

        // Perform the POST request with authentication
        MvcResult result = mockMvc.perform(post("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.authentication(auth))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        // Verify the response
        if (responseContent.isEmpty()) {
            fail("Response content is empty. Status: " + response.getStatus());
        }

        String expectedJson = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\",\"createdAt\":\"" + userDto.getCreatedAt().toString() + "\"}";
        JSONAssert.assertEquals(expectedJson, responseContent, false);

        verify(userService, times(1)).saveOrUpdate(any(UserDto.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.saveOrUpdate(any(UserDto.class))).thenReturn(userDto);

        // Create a mock Authentication object
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("john.doe@example.com", null, authorities);

        // Perform the PUT request with authentication
        MvcResult result = mockMvc.perform(put("/api/users/1")
                .with(SecurityMockMvcRequestPostProcessors.authentication(auth))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        // Verify the response
        if (responseContent.isEmpty()) {
            fail("Response content is empty. Status: " + response.getStatus());
        }

        String expectedJson = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"role\":\"USER\",\"createdAt\":\"" + userDto.getCreatedAt().toString() + "\"}";
        JSONAssert.assertEquals(expectedJson, responseContent, false);

        verify(userService, times(1)).saveOrUpdate(any(UserDto.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.getById(1L)).thenReturn(userDto);
        doNothing().when(userService).deleteById(1L);

        // Create a mock Authentication object
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken("john.doe@example.com", null, authorities);

        // Perform the DELETE request with authentication
        MvcResult result = mockMvc.perform(delete("/api/users/1")
                .with(SecurityMockMvcRequestPostProcessors.authentication(auth))
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        // Verify the response
        assertEquals("User with id = 1 deleted.", responseContent);

        verify(userService, times(1)).getById(1L);
        verify(userService, times(1)).deleteById(1L);
    }


}
