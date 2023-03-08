package com.github.massimopavoni.restamongo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAdvisorTest {
    ControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new ControllerAdvisor();
    }

    @Test
    void nullPointerHandler() {
        NullPointerException exception = assertThrowsExactly(NullPointerException.class, () -> {
            throw new NullPointerException("");
        });
        Map<String, Object> info = advisor.nullPointerHandler(exception);
        assertAll(() -> assertEquals(2, info.size()),
                  () -> assertEquals("Content has null instances.", info.get("message")),
                  () -> assertTrue(info.get("innerMessage").toString().isEmpty()));
    }
}
