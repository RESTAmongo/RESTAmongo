package com.github.massimopavoni.restamongo;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ApplicationTest {
    @Test
    void mainRun() {
        assertDoesNotThrow(() -> Application.main(new String[] {}));
    }

    @Test
    void propertySourcesPlaceholderConfigurerNonExistent() {
        String tempProp = System.getProperty("user.home");
        System.setProperty("user.home", "/dev/null");
        assertThrowsExactly(FileNotFoundException.class, Application::propertySourcesPlaceholderConfigurer);
        System.setProperty("user.home", tempProp);
    }
}
