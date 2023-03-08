package com.github.massimopavoni.restamongo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {
    @Test
    void mainRun() {
        assertDoesNotThrow(() -> Application.main(new String[] {}));
    }
}
