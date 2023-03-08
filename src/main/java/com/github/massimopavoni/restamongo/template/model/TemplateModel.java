package com.github.massimopavoni.restamongo.template.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Template model from API request body.
 */
public record TemplateModel(String id, List<Property> properties) {
    /**
     * Properties defining the template.
     *
     * @return copy of list of properties
     */
    @Override
    public List<Property> properties() {
        return new ArrayList<>(this.properties);
    }
}
