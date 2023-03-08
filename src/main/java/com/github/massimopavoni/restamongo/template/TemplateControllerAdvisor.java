package com.github.massimopavoni.restamongo.template;

import com.github.massimopavoni.restamongo.ResponseConstants;
import com.github.massimopavoni.restamongo.exceptions.DocumentAlreadyExistsException;
import com.github.massimopavoni.restamongo.exceptions.DocumentNotFoundException;
import com.github.massimopavoni.restamongo.exceptions.InvalidDocumentContentException;
import com.google.common.collect.ImmutableSortedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Template controller advisor with handlers.
 */
@ControllerAdvice
public class TemplateControllerAdvisor {
    /**
     * Handler providing further information for the not found status for non-existent templates.
     *
     * @param exception exception triggering the handler
     *
     * @return info to be added to response body
     */
    @ResponseBody
    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> templateNotFoundHandler(DocumentNotFoundException exception) {
        return ImmutableSortedMap.of(ResponseConstants.MESSAGE_KEY, "Specified template does not exist.",
                                     ResponseConstants.INNER_MESSAGE_KEY, exception.getMessage());
    }

    /**
     * Handler providing further information for the conflict status for already existing templates.
     *
     * @param exception exception triggering the handler
     *
     * @return info to be added to response body
     */
    @ResponseBody
    @ExceptionHandler(DocumentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> templateAlreadyExistsHandler(DocumentAlreadyExistsException exception) {
        return ImmutableSortedMap.of(ResponseConstants.MESSAGE_KEY, "Specified template already exists.",
                                     ResponseConstants.INNER_MESSAGE_KEY, exception.getMessage());
    }

    /**
     * Handler providing further information for the unprocessable entity status for invalid templates contents.
     *
     * @param exception exception triggering the handler
     *
     * @return info to be added to response body
     */
    @ResponseBody
    @ExceptionHandler(InvalidDocumentContentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, Object> invalidTemplateContentHandler(InvalidDocumentContentException exception) {
        return ImmutableSortedMap.of(ResponseConstants.MESSAGE_KEY, "Specified template content is invalid.",
                                     ResponseConstants.INNER_MESSAGE_KEY, exception.getMessage());
    }
}
