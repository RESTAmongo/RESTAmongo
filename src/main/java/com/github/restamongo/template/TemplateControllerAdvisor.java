package com.github.restamongo.template;

import com.github.restamongo.exceptions.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Template controller advisor with handlers.
 */
@ControllerAdvice
public class TemplateControllerAdvisor {
    /**
     * Handler providing further information for the not found status.
     *
     * @param exception inner exception
     *
     * @return info to be added to response body
     */
    @ResponseBody
    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> templateNotFoundHandler(DocumentNotFoundException exception) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("message", "Specified template not found");
        info.put("innerMessage", exception.getMessage());
        info.put("timestamp", LocalDateTime.now());
        return info;
    }
}
