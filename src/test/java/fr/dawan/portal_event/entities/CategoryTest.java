package fr.dawan.portal_event.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    private Category category;

    @BeforeEach
    void setup(){
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
    }

    @Test
    void testCategory() {
        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
    }
}
