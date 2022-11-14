package com.github.restamongo.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class DocumentNotFoundExceptionTest {
    @Test
    void constructorEmpty() {
        Exception exception = assertThrowsExactly(DocumentNotFoundException.class, () -> {
            throw new DocumentNotFoundException();
        });
        assertEquals("Specified document does not exist.", exception.getMessage());
    }

    @Test
    void constructorStringId() {
        Exception exception = assertThrowsExactly(DocumentNotFoundException.class, () -> {
            throw new DocumentNotFoundException("error");
        });
        assertEquals("Document with id 'error' does not exist.", exception.getMessage());
    }
}
