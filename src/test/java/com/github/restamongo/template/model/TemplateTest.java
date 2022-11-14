package com.github.restamongo.template.model;

import com.github.restamongo.template.TemplateModel;
import org.junit.jupiter.api.Test;

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
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()));
    }

    @Test
    void constructorFields() {
        Template template = new Template("archetype", Collections.singletonList(
                new Property("color", 0, Collections.emptyList())));
        assertAll(() -> assertEquals("archetype", template.id),
                  () -> assertEquals("color", template.properties.get(0).name),
                  () -> assertEquals(0, template.properties.get(0).order),
                  () -> assertTrue(template.properties.get(0).embeddedProperties.isEmpty()));
    }
}