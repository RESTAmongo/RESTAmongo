package com.github.massimopavoni.restamongo.template;

import com.github.massimopavoni.restamongo.exceptions.DocumentAlreadyExistsException;
import com.github.massimopavoni.restamongo.exceptions.DocumentNotFoundException;
import com.github.massimopavoni.restamongo.exceptions.InvalidDocumentContentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateControllerAdvisorTest {
    TemplateControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new TemplateControllerAdvisor();
    }

    @Test
    void templateNotFoundHandler() {
        DocumentNotFoundException exception = assertThrowsExactly(DocumentNotFoundException.class, () -> {
            throw new DocumentNotFoundException();
        });
        Map<String, Object> info = advisor.templateNotFoundHandler(exception);
        assertAll(() -> assertEquals(2, info.size()),
                  () -> assertEquals("Specified template does not exist.", info.get("message")),
                  () -> assertEquals("Specified document does not exist.", info.get("innerMessage")));
    }

    @Test
    void templateAlreadyExistsHandler() {
        DocumentAlreadyExistsException exception = assertThrowsExactly(DocumentAlreadyExistsException.class, () -> {
            throw new DocumentAlreadyExistsException();
        });
        Map<String, Object> info = advisor.templateAlreadyExistsHandler(exception);
        assertAll(() -> assertEquals(2, info.size()),
                  () -> assertEquals("Specified template already exists.", info.get("message")),
                  () -> assertEquals("Specified document already exists.", info.get("innerMessage")));
    }

    @Test
    void invalidTemplateContentHandler() {
        InvalidDocumentContentException exception = assertThrowsExactly(InvalidDocumentContentException.class, () -> {
            throw new InvalidDocumentContentException();
        });
        Map<String, Object> info = advisor.invalidTemplateContentHandler(exception);
        assertAll(() -> assertEquals(2, info.size()),
                  () -> assertEquals("Specified template content is invalid.", info.get("message")),
                  () -> assertEquals("Specified document content is invalid.", info.get("innerMessage")));
    }
}