package com.github.massimopavoni.restamongo.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class InvalidDocumentContentExceptionTest {
    @Test
    void constructorEmpty() {
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () -> {
            throw new InvalidDocumentContentException();
        });
        assertEquals("Specified document content is invalid.", exception.getMessage());
    }

    @Test
    void constructorStringId() {
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () -> {
            throw new InvalidDocumentContentException("being invalid");
        });
        assertEquals("Specified document content is invalid due to: being invalid.", exception.getMessage());
    }
}
