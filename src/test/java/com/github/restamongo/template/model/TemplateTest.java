package com.github.restamongo.template.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {
    @Test
    void constructorEmpty() {
        Template template = new Template();
        assertNotNull(template);
    }

    @Test
    void constructorRequestModel() {
        Template template = new Template(
                new TemplateModel("archetype", Collections.singletonList(
                        new Property("color", 0, Collections.emptyList()))));
        assertAll(() -> assertEquals("archetype", template.id),
                  () -> assertEquals(1, template.properties.size()),
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()));
    }

    @Test
    void constructorFields() {
        Template template = new Template("archetype", Arrays.asList(
                new Property("shape", 1, Collections.emptyList()),
                new Property("color", 0, Collections.emptyList())));
        assertAll(() -> assertEquals("archetype", template.id),
                  () -> assertEquals(2, template.properties.size()),
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()),
                  () -> assertEquals("shape", template.properties.get(1).name),
                  () -> assertEquals(1, template.properties.get(1).order),
                  () -> assertTrue(template.properties.get(1).embeddedProperties.isEmpty()));
    }
}