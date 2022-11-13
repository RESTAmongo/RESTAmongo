package com.github.restamongo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {
    @Test
    void applicationMain() {
        Application.main(new String[] {});
        assertTrue(true);
    }
}
