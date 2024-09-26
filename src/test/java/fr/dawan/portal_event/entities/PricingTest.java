package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PricingTest {
    private Pricing pricing;

    @BeforeEach
    public void setup(){
        pricing = new Pricing();
    }

    @Test
    public void testPricing() {
        assertEquals(1L, pricing.getId());
        assertEquals(10.0, pricing.getPrice());
        assertEquals("Test Pricing", pricing.getName());
    }
}
