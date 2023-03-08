package com.github.massimopavoni.restamongo.template;

import com.github.massimopavoni.restamongo.exceptions.DocumentAlreadyExistsException;
import com.github.massimopavoni.restamongo.exceptions.DocumentNotFoundException;
import com.github.massimopavoni.restamongo.exceptions.InvalidDocumentContentException;
import com.github.massimopavoni.restamongo.template.model.Property;
import com.github.massimopavoni.restamongo.template.model.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TemplateServiceTest {
    Map<String, Template> repositoryTemplates;
    @MockBean
    TemplateRepository repository;
    @Autowired
    TemplateService service;

    @BeforeEach
    void setUp() {
        repositoryTemplates = new LinkedHashMap<>() {{
            put("archetype", new Template("archetype", Collections.singletonList(
                    new Property("color", 0, Collections.emptyList()))));
            put("cube", new Template("cube", Collections.singletonList(
                    new Property("size", 0, Collections.emptyList()))));
        }};
    }

    @Test
    void findAllEmpty() {
        doReturn(Collections.emptyList()).when(repository).findAll();
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void findAllItems() {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        List<Template> serviceTemplates = service.findAll();
        assertAll(() -> assertEquals(repositoryTemplates.get("archetype"), serviceTemplates.get(0)),
                  () -> assertEquals(repositoryTemplates.get("cube"), serviceTemplates.get(1)));
    }

    @Test
    void findOneNotFound() {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        Exception exception = assertThrowsExactly(DocumentNotFoundException.class, () -> service.findOne("void"));
        assertEquals("Document with id \"void\" does not exist.", exception.getMessage());
    }

    @Test
    void findOneFound() {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        Template archetype = service.findOne("archetype");
        Template cube = service.findOne("cube");
        assertAll(() -> assertEquals(repositoryTemplates.get("archetype"), archetype),
                  () -> assertEquals(repositoryTemplates.get("cube"), cube));
    }

    @Test
    void validateTemplateBlankId() {
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () ->
                service.create(new Template("", Collections.singletonList(
                        new Property("power", 1, Collections.emptyList())))));
        assertEquals("Specified document content is invalid due to: template must have a non-empty id.",
                     exception.getMessage());
    }

    @Test
    void validateTemplateNoProperties() {
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () ->
                service.create(new Template("archetype", Collections.emptyList())));
        assertEquals("Specified document content is invalid due to: template does not have any property.",
                     exception.getMessage());
    }

    @Test
    void validateTemplateBlankProperty() {
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () ->
                service.create(new Template("archetype", Collections.singletonList(
                        new Property("", 1, Collections.emptyList())))));
        assertEquals("Specified document content is invalid due to: property must have a non-empty name.",
                     exception.getMessage());
    }


    @Test
    void validateTemplateNonExistentProperty() {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () ->
                service.create(new Template("archetype", Collections.singletonList(
                        new Property("power", 1, Collections.singletonList("type"))))));
        assertEquals("Specified document content is invalid due to: \"power\" property template does not exist.",
                     exception.getMessage());
    }

    @Test
    void validateTemplateNonExistentEmbeddedProperty() {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        Exception exception = assertThrowsExactly(InvalidDocumentContentException.class, () ->
                service.create(new Template("archetype", Collections.singletonList(
                        new Property("archetype", 1, Arrays.asList("color", "power"))))));
        assertEquals("Specified document content is invalid due to: " +
                     "\"archetype\" property template does not have property \"power\".",
                     exception.getMessage());
    }

    @Test
    void createAlreadyExists() {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        Exception exception = assertThrowsExactly(DocumentAlreadyExistsException.class, () ->
                service.create(new Template("archetype", Collections.singletonList(
                        new Property("archetype", 1, Collections.singletonList("color"))))));
        assertEquals("Document with id \"archetype\" already exists.", exception.getMessage());
    }

    @Test
    void createSuccess() {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        doAnswer((Answer<Template>) invocation -> {
            Template template = invocation.getArgument(0);
            repositoryTemplates.put(template.id, template);
            return template;
        }).when(repository).save(any(Template.class));
        Template template = new Template("monster", Collections.singletonList(
                new Property("archetype", 1, Collections.singletonList("color"))));
        assertAll(() -> assertEquals(2, service.findAll().size()),
                  () -> assertEquals(template, service.create(template)),
                  () -> assertEquals(3, service.findAll().size()),
                  () -> assertEquals(template, service.findOne("monster")));
    }

    @Test
    void updateExisting() {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Template>) invocation -> {
            Template template = invocation.getArgument(0);
            repositoryTemplates.put(template.id, template);
            return template;
        }).when(repository).save(any(Template.class));
        Template template = new Template("cube", Collections.singletonList(
                new Property("archetype", 1, Collections.emptyList())));
        assertAll(() -> assertEquals(2, service.findAll().size()),
                  () -> assertEquals(template, service.update(template)),
                  () -> assertEquals(2, service.findAll().size()),
                  () -> assertEquals(template, service.findOne("cube")));
    }

    @Test
    void updateCreate() {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Template>) invocation -> {
            Template template = invocation.getArgument(0);
            repositoryTemplates.put(template.id, template);
            return template;
        }).when(repository).save(any(Template.class));
        Template template = new Template("monster", Collections.singletonList(
                new Property("archetype", 1, Collections.singletonList("color"))));
        assertAll(() -> assertEquals(2, service.findAll().size()),
                  () -> assertEquals(template, service.update(template)),
                  () -> assertEquals(3, service.findAll().size()),
                  () -> assertEquals(template, service.findOne("monster")));
    }

    @Test
    void deleteNotFound() {
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        Exception exception = assertThrowsExactly(DocumentNotFoundException.class, () -> service.delete("void"));
        assertEquals("Document with id \"void\" does not exist.", exception.getMessage());
    }

    @Test
    void deleteSuccess() {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        doAnswer((Answer<Void>) invocation -> {
            repositoryTemplates.remove((String) invocation.getArgument(0));
            return null;
        }).when(repository).deleteById(anyString());
        assertAll(() -> assertEquals(2, service.findAll().size()),
                  () -> assertDoesNotThrow(() -> service.delete("archetype")),
                  () -> assertEquals(1, service.findAll().size()),
                  () -> assertEquals("cube", service.findAll().get(0).id));
    }
}