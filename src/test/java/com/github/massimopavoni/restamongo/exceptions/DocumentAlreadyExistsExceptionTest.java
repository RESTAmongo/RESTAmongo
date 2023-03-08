package com.github.massimopavoni.restamongo.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class DocumentAlreadyExistsExceptionTest {
    @Test
    void constructorEmpty() {
        Exception exception = assertThrowsExactly(DocumentAlreadyExistsException.class, () -> {
            throw new DocumentAlreadyExistsException();
        });
        assertEquals("Specified document already exists.", exception.getMessage());
    }

    @Test
    void constructorStringId() {
        Exception exception = assertThrowsExactly(DocumentAlreadyExistsException.class, () -> {
            throw new DocumentAlreadyExistsException("error");
        });
        assertEquals("Document with id \"error\" already exists.", exception.getMessage());
    }
}
