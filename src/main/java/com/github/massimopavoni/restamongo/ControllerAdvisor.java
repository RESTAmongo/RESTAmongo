package com.github.massimopavoni.restamongo;

import com.google.common.collect.ImmutableSortedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Controller advisor with handlers.
 */
@ControllerAdvice
public class ControllerAdvisor {
    /**
     * Handler providing further information for the bad request status for null pointer content.
     *
     * @param exception exception triggering the handler
     *
     * @return info to be added to response body
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> nullPointerHandler(NullPointerException exception) {
        return ImmutableSortedMap.of(ResponseConstants.MESSAGE_KEY, "Content has null instances.",
                                     ResponseConstants.INNER_MESSAGE_KEY, exception.getMessage());
    }
}
