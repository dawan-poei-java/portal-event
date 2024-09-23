package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventTest {
    private Event event;
    private LocalDateTime now;

    @BeforeEach
    void setup(){
        now = LocalDateTime.now();
        event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setDescription("This is a test event.");
        event.setStartDate(now);
        event.setEndDate(now.plusDays(1));
        event.setStartTime(LocalTime.of(10, 0));
        event.setEndTime(LocalTime.of(18, 0));
        event.setLocation("Test Location");
        event.setAddress("123 Test Street");
        event.setAddressComplement("Apt 4B");
        event.setCity(new City()); // You can mock this if needed
        event.setZipCode("12345");
        event.setPricings(new ArrayList<>());
        event.setOrganizer(new User()); // You can mock this if needed
        event.setParticipants(new ArrayList<>());
    }

    @Test
    void testEvent() {
        // TODO: Implement test cases
        assertEquals(1L, event.getId());
        assertEquals("Test Event", event.getTitle());
        assertEquals("This is a test event.", event.getDescription());
        assertEquals(now, event.getStartDate());
        assertEquals(now.plusDays(1), event.getEndDate());
        assertEquals(LocalTime.of(10, 0), event.getStartTime());
        assertEquals(LocalTime.of(18, 0), event.getEndTime());
        assertEquals("Test Location", event.getLocation());
        assertEquals("123 Test Street", event.getAddress());
        assertEquals("Apt 4B", event.getAddressComplement());
        assertEquals(new City(), event.getCity());
        assertEquals("12345", event.getZipCode());
        assertEquals(new ArrayList<>(), event.getPricings());
    }

    @Test
    void testAddParticipant() {
        User participant = new User();
        event.getParticipants().add(participant);
        assertTrue(event.getParticipants().contains(participant));
    }

    @Test
    void testRemoveParticipant() {
        User participant = new User();
        event.getParticipants().add(participant);
        event.getParticipants().remove(participant);
        assertFalse(event.getParticipants().contains(participant));
    }

}
