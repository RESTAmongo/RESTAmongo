package com.github.restamongo.template;

import com.github.restamongo.exceptions.DocumentAlreadyExistsException;
import com.github.restamongo.exceptions.DocumentNotFoundException;
import com.github.restamongo.exceptions.InvalidDocumentContentException;
import com.github.restamongo.template.model.Property;
import com.github.restamongo.template.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Template service for actions on repository.
 */
@Service
public class TemplateService {
    /**
     * Template repository instance.
     */
    @Autowired
    private TemplateRepository repository;

    /**
     * Find all templates.
     *
     * @return list of templates
     */
    public List<Template> findAll() {
        return this.repository.findAll();
    }

    /**
     * Find one template with id.
     *
     * @param id unique string id
     *
     * @return specified template
     */
    public Template findOne(String id) {
        return this.repository.findById(id).orElseThrow(() -> new DocumentNotFoundException(id));
    }

    /**
     * Create new template.
     *
     * @param template model from request body
     *
     * @return newly created template
     */
    public Template create(Template template) {
        this.validateTemplate(template);
        if (this.repository.existsById(template.id))
            throw new DocumentAlreadyExistsException(template.id);
        else
            return this.repository.save(template);
    }

    /**
     * Update an existing template or create a new one.
     *
     * @param template model from request body
     *
     * @return updated or created template
     */
    public Template update(Template template) {
        this.validateTemplate(template);
        return this.repository.save(template);
    }

    /**
     * Delete an existing template.
     *
     * @param id unique string id
     */
    public void delete(String id) {
        if (this.repository.existsById(id))
            this.repository.deleteById(id);
        else
            throw new DocumentNotFoundException(id);
    }

    /**
     * Validation check on template:
     * - template id must be non-empty;
     * - there must be at least one property;
     * - properties names must be non-empty;
     * - if there are embedded properties, template properties must correspond to existing templates.
     *
     * @param template template to check
     */
    private void validateTemplate(Template template) {
        if (template.id.isBlank())
            throw new InvalidDocumentContentException("template must have a non-empty id");
        if (template.properties.isEmpty())
            throw new InvalidDocumentContentException("template does not have any property");
        for (Property property : template.properties) {
            if (property.name.isBlank())
                throw new InvalidDocumentContentException("property must have a non-empty name");
            if (!property.embeddedProperties.isEmpty())
                this.repository.findById(property.name).ifPresentOrElse(t -> {
                    List<String> tPropertiesNames = t.properties.stream().map(p -> p.name).toList();
                    property.embeddedProperties.forEach(ep -> {
                        if (!tPropertiesNames.contains(ep))
                            throw new InvalidDocumentContentException(
                                    String.format("\"%s\" property template does not have property \"%s\"", t.id, ep));
                    });
                }, () -> {
                    throw new InvalidDocumentContentException(
                            String.format("\"%s\" property template does not exist", property.name));
                });
        }
    }
}
