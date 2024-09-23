package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CityTest {
    private City city;

    @BeforeEach
    void setup(){
        city = new City();
        city.setId(1L);
        city.setName("Test City");
    }

    @Test
    void testCity() {
        assertEquals(1L, city.getId());
        assertEquals("Test City", city.getName());
    }
}
