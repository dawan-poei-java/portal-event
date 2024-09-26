package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeEventTest {
    private TypeEvent typeEvent;

    @BeforeEach
    public void setup(){
        typeEvent = new TypeEvent();
    }

    @Test
    public void testTypeEvent() {
        assertEquals(1L, typeEvent.getId());
        assertEquals("Test Type Event", typeEvent.getName());
    }
}
