package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dto.request.ProjectRequest;
import be.vives.ti.CheckIt.service.ProjectService;
import be.vives.ti.CheckIt.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTest {
    private final String baseUrl = "/projects";

    @MockBean
    private ProjectService projectService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private Project school;
    private Project hobby;
    private Project friends;

    @BeforeEach
    void setUp() {
        school = new Project(1L, "School", "Study hard!");
        hobby = new Project(2L, "Hobby", "free time!");
        friends = new Project(3L, "Friends", "have some fun!");
    }

    @Test
    void getAllProjects() throws Exception {
        List<Project> list = Arrays.asList(school, hobby, friends);
        when(projectService.getAllProjects()).thenReturn(list);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(school.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(hobby.getId()))))
                .andExpect(jsonPath("$[2].id", is(Math.toIntExact(friends.getId()))));
    }

    @Test
    void getProjectById() throws Exception {
        int projectId = 2;

        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(hobby));

        mvc.perform(get(baseUrl + "/" + projectId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(hobby.getId()))));
    }

    @Test
    void getProjectByIdNotFound() throws Exception {
        int projectId = 5;

        when(projectService.getProjectById(projectId)).thenReturn(Optional.empty());

        mvc.perform(get(baseUrl + "/" + projectId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createProject() throws Exception {
        Project sports = new Project(4L, "Sports", "stay healthy");

        when(projectService.saveProject(any(Project.class))).thenReturn(sports);

        ProjectRequest sportsRequest = new ProjectRequest(sports.getName(), sports.getDescription());

        mvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sportsRequest)))
                            .andExpect(status().isCreated())
                            .andExpect(header().string("location", "http://localhost/projects/add/4"));

    }

    @Test
    void createProjectValidationError() throws Exception {

        ProjectRequest sportsRequest = new ProjectRequest("", "");

        mvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sportsRequest)))
                .andExpect(status().isBadRequest());

    }
    //TODO test delete project
}
