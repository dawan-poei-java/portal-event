package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.dawan.portal_event.enums.UserRole;

public class UserTest {
    private User user;

    @BeforeEach
    public void setup(){
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("2vqkA@example.com");
        user.setPassword("password");
        user.setPhoneNumber("123456789");
        user.setAddress("123 Main Street");
        user.setAddressComplement("Apt. 456");
        user.setCity(new City());
        user.setZipCode("12345");
        user.setUserRole(UserRole.ORGANIZER);
    }

    @Test
    void testUser() {
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("2vqkA@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("123 Main Street", user.getAddress());
        assertEquals("Apt. 456", user.getAddressComplement());
        assertEquals("12345", user.getZipCode());
        assertEquals(UserRole.ORGANIZER, user.getUserRole());
    }
}

    
