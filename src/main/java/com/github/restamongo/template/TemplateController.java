package com.github.restamongo.template;

import com.github.restamongo.template.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Template API controller.
 */
@RestController
@RequestMapping("templates")
public class TemplateController {
    /**
     * Template service instance.
     */
    @Autowired
    private TemplateService service;

    /**
     * Get all templates.
     *
     * @return list of templates
     */
    @GetMapping
    public List<Template> getAll() {
        return service.find();
    }

    /**
     * Get one template with id.
     *
     * @param id unique string id
     *
     * @return specified template
     */
    @GetMapping("{id}")
    public Template getOne(@PathVariable String id) {
        return service.find(id);
    }

    /**
     * Post a new template.
     *
     * @param templateModel model from request body
     *
     * @return newly created template
     */
    @PostMapping
    public Template post(@RequestBody TemplateModel templateModel) {
        return service.create(templateModel);
    }

    /**
     * Put a new or existing template.
     *
     * @param id            unique string id
     * @param templateModel model from request body
     *
     * @return updated or created template
     */
    @PutMapping("{id}")
    public Template put(@PathVariable String id, @RequestBody TemplateModel templateModel) {
        return service.update(id, templateModel);
    }

    /**
     * Delete an existing template.
     *
     * @param id unique string id
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}