package com.github.restamongo.template;

import com.github.restamongo.template.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Template repository for data management.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {
}
