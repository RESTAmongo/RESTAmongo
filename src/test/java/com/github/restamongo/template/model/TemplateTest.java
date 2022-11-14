package com.github.restamongo.template.model;

import com.github.restamongo.template.TemplateModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {
    @Test
    void emptyConstructor() {
        Template template = new Template();
        assertNotNull(template);
    }

    @Test
    void requestModelConstructor() {
        Template template = new Template(
                new TemplateModel("archetype", Collections.singletonList(
                        new Property("color", 0, Collections.emptyList()))));
        assertAll(() -> assertEquals("archetype", template.id),
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()));
    }

    @Test
    void fieldsConstructor() {
        Template template = new Template("archetype", Collections.singletonList(
                new Property("color", 0, Collections.emptyList())));
        assertAll(() -> assertEquals("archetype", template.id),
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()));
    }
}