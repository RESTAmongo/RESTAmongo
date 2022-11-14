package com.github.restamongo.template.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * Template property data class.
 */
public class Property {
    /**
     * Property name.
     */
    @Field(targetType = FieldType.STRING)
    public String name;
    /**
     * Property visualization order.
     */
    @Field(targetType = FieldType.INT32)
    public int order;
    /**
     * Properties to embed: if the list is not empty, this property must correspond to an existing template.
     */
    @Field(targetType = FieldType.ARRAY)
    public List<String> embeddedProperties;

    /**
     * Default constructor with fields.
     *
     * @param name               property name
     * @param order              property visualization order
     * @param embeddedProperties list of properties to embed
     */
    public Property(String name, int order, List<String> embeddedProperties) {
        this.name = name;
        this.order = order;
        this.embeddedProperties = embeddedProperties;
    }
}