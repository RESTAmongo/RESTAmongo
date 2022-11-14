package com.github.restamongo.template.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    @Test
    void fieldsConstructor() {
        Property property = new Property("color", 0, Collections.emptyList());
        assertAll(() -> assertEquals("color", property.name),
                  () -> assertEquals(0, property.order),
                  () -> assertTrue(property.embeddedProperties.isEmpty()));
    }
}