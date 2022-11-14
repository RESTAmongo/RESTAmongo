package com.github.restamongo.template.model;

import com.github.restamongo.template.TemplateModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * Document template data class.
 */
@Document(collection = "templates")
public class Template {
    /**
     * Unique template string id.
     */
    @MongoId(FieldType.STRING)
    public String id;
    /**
     * Properties defining the template.
     */
    @Field(targetType = FieldType.ARRAY)
    public List<Property> properties;

    /**
     * Empty constructor.
     */
    public Template() {
    }

    /**
     * Constructor from API request body model.
     *
     * @param templateModel model from request body
     */
    public Template(TemplateModel templateModel) {
        this(templateModel.id(), templateModel.properties());
    }

    /**
     * Constructor with fields.
     *
     * @param id         unique string id
     * @param properties list of properties
     */
    public Template(String id, List<Property> properties) {
        this.id = id;
        this.properties = properties;
    }
}