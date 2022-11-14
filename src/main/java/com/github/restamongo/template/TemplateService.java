package com.github.restamongo.template;

import com.github.restamongo.exceptions.DocumentNotFoundException;
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
    public List<Template> find() {
        return repository.findAll();
    }

    /**
     * Find one template with id.
     *
     * @param id unique string id
     *
     * @return specified template
     */
    public Template find(String id) {
        return repository.findById(id).orElseThrow(() -> new DocumentNotFoundException(id));
    }

    /**
     * Create new template.
     *
     * @param templateModel model from request body
     *
     * @return newly created template
     */
    public Template create(TemplateModel templateModel) {
        return repository.save(new Template(templateModel));
    }

    /**
     * Update an existing template or create a new one.
     *
     * @param id            unique string id
     * @param templateModel model from request body
     *
     * @return updated or created template
     */
    public Template update(String id, TemplateModel templateModel) {
        return repository.findById(id).map(t -> {
            t.properties = templateModel.properties();
            return repository.save(t);
        }).orElseGet(() -> repository.save(new Template(id, templateModel.properties())));
    }

    /**
     * Delete an existing template.
     *
     * @param id unique string id
     */
    public void delete(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new DocumentNotFoundException(id);
    }
}
