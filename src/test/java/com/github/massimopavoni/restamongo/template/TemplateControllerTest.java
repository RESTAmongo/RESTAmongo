package com.github.massimopavoni.restamongo.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.massimopavoni.restamongo.template.model.Property;
import com.github.massimopavoni.restamongo.template.model.Template;
import com.github.massimopavoni.restamongo.template.model.TemplateModel;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TemplateController.class)
@ComponentScan
class TemplateControllerTest {
    Map<String, Template> repositoryTemplates;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TemplateRepository repository;

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
    void getAllEmpty() throws Exception {
        doReturn(Collections.emptyList()).when(repository).findAll();
        mockMvc.perform(get("/api/templates"))
               .andDo(print())
               .andExpectAll(status().isOk(),
                             jsonPath("$", empty()));
    }

    @Test
    void getAllSuccess() throws Exception {
        doAnswer((Answer<List<Template>>) invocation ->
                repositoryTemplates.values().stream().toList()).when(repository).findAll();
        mockMvc.perform(get("/api/templates"))
               .andDo(print())
               .andExpectAll(status().isOk(),
                             jsonPath("$", containsInAnyOrder(
                                     equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                             repositoryTemplates.get("archetype")), "$")),
                                     equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                             repositoryTemplates.get("cube")), "$")))));
    }

    @Test
    void getOneNotFound() throws Exception {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        mockMvc.perform(get("/api/templates/void"))
               .andDo(print())
               .andExpectAll(status().isNotFound(),
                             jsonPath("$.message", equalTo("Specified template does not exist.")));
    }

    @Test
    void getOneFound() throws Exception {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        mockMvc.perform(get("/api/templates/archetype"))
               .andDo(print())
               .andExpectAll(status().isOk(),
                             jsonPath("$", equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                     repositoryTemplates.get("archetype")), "$"))));
    }

    @Test
    void postInvalid() throws Exception {
        TemplateModel templateModel = new TemplateModel("", Collections.emptyList());
        mockMvc.perform(post("/api/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(templateModel)))
               .andDo(print())
               .andExpectAll(status().isUnprocessableEntity(),
                             jsonPath("$.message", equalTo("Specified template content is invalid.")));
    }

    @Test
    void postAlreadyExisting() throws Exception {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        TemplateModel templateModel = new TemplateModel("archetype", Collections.singletonList(
                new Property("power", 1, Collections.emptyList())));
        mockMvc.perform(post("/api/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(templateModel)))
               .andDo(print())
               .andExpectAll(status().isConflict(),
                             jsonPath("$.message", equalTo("Specified template already exists.")));
    }

    @Test
    void postSuccess() throws Exception {
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
        TemplateModel templateModel = new TemplateModel("monster", Arrays.asList(
                new Property("power", 1, Collections.emptyList()),
                new Property("archetype", 0, Collections.singletonList("color"))));
        mockMvc.perform(post("/api/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(templateModel)))
               .andDo(print())
               .andExpectAll(status().isCreated(),
                             jsonPath("$", equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                     repositoryTemplates.get("monster")), "$"))));
    }

    @Test
    void putUpdateExisting() throws Exception {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Template>) invocation -> {
            Template template = invocation.getArgument(0);
            repositoryTemplates.put(template.id, template);
            return template;
        }).when(repository).save(any(Template.class));
        TemplateModel templateModel = new TemplateModel("archetype", Arrays.asList(
                new Property("power", 1, Collections.emptyList()),
                new Property("archetype", 0, Collections.singletonList("color"))));
        mockMvc.perform(put("/api/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(templateModel)))
               .andDo(print())
               .andExpectAll(status().isOk(),
                             jsonPath("$", equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                     repositoryTemplates.get("archetype")), "$"))));
    }

    @Test
    void putUpdateCreate() throws Exception {
        doAnswer((Answer<Optional<Template>>) invocation ->
                Optional.ofNullable(repositoryTemplates.get((String) invocation.getArgument(0))))
                .when(repository).findById(anyString());
        doAnswer((Answer<Template>) invocation -> {
            Template template = invocation.getArgument(0);
            repositoryTemplates.put(template.id, template);
            return template;
        }).when(repository).save(any(Template.class));
        TemplateModel templateModel = new TemplateModel("monster", Arrays.asList(
                new Property("power", 1, Collections.emptyList()),
                new Property("archetype", 0, Collections.singletonList("color"))));
        mockMvc.perform(put("/api/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(templateModel)))
               .andDo(print())
               .andExpectAll(status().isOk(),
                             jsonPath("$", equalTo(JsonPath.read(objectMapper.writeValueAsString(
                                     repositoryTemplates.get("monster")), "$"))));
    }

    @Test
    void deleteNotFound() throws Exception {
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        mockMvc.perform(delete("/api/templates/void"))
               .andDo(print())
               .andExpectAll(status().isNotFound(),
                             jsonPath("$.message", equalTo("Specified template does not exist.")));
    }

    @Test
    void deleteSuccess() throws Exception {
        doAnswer((Answer<Boolean>) invocation ->
                repositoryTemplates.containsKey((String) invocation.getArgument(0)))
                .when(repository).existsById(anyString());
        doAnswer((Answer<Void>) invocation -> {
            repositoryTemplates.remove((String) invocation.getArgument(0));
            return null;
        }).when(repository).deleteById(anyString());
        mockMvc.perform(delete("/api/templates/archetype"))
               .andDo(print())
               .andExpectAll(status().isNoContent(),
                             jsonPath("$").doesNotExist());
        assertEquals(Collections.singletonList("cube"), repositoryTemplates.keySet().stream().toList());
    }
}