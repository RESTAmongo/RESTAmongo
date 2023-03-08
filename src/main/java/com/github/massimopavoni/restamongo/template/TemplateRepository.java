package com.github.massimopavoni.restamongo.template;

import com.github.massimopavoni.restamongo.template.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Template repository for data management.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {
}
