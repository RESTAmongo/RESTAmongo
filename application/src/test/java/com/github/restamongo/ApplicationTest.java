package com.github.restamongo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationTest {
    @Test
    void appHasAGreeting() {
        Application classUnderTest = new Application();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
