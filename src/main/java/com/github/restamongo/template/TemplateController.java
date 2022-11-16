package com.github.restamongo.template;

import com.github.restamongo.template.model.Template;
import com.github.restamongo.template.model.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Template API controller.
 */
@RestController
@RequestMapping("api/templates")
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
    @ResponseStatus(HttpStatus.OK)
    public List<Template> getAll() {
        return service.findAll();
    }

    /**
     * Get one template with id.
     *
     * @param id unique string id
     *
     * @return specified template
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Template getOne(@PathVariable String id) {
        return service.findOne(id);
    }

    /**
     * Post a new template.
     *
     * @param templateModel model from request body
     *
     * @return newly created template
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Template post(@RequestBody TemplateModel templateModel) {
        return service.create(new Template(templateModel));
    }

    /**
     * Put a new or existing template.
     *
     * @param templateModel model from request body
     *
     * @return updated or created template
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Template put(@RequestBody TemplateModel templateModel) {
        return service.update(new Template(templateModel));
    }

    /**
     * Delete an existing template.
     *
     * @param id unique string id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}