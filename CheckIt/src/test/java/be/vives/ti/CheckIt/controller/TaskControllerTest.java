package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.CategoryRepository;
import be.vives.ti.CheckIt.dao.PriorityRepository;
import be.vives.ti.CheckIt.dao.ProjectRepository;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.dto.request.ProjectRequest;
import be.vives.ti.CheckIt.dto.request.TaskRequest;
import be.vives.ti.CheckIt.service.CategoryService;
import be.vives.ti.CheckIt.service.PriorityService;
import be.vives.ti.CheckIt.service.ProjectService;
import be.vives.ti.CheckIt.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {
    private final String baseUrl = "/tasks";

    private Timestamp timestamp;

    @MockBean
    private TaskService taskService;
    @MockBean
    private ProjectService projectService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private PriorityService priorityService;

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private PriorityRepository priorityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private Task javaBackend;
    private Task deployApi;
    private Task crossPlatformDevelopment;
    private Task bowling;

    private Project school;
    private Project sports;

    private Category it;
    private Category friends;

    private Priority highpriority;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2024-12-22 12:00:00", formatter);
        timestamp = Timestamp.valueOf(localDateTime);

        school = new Project(1L, "School", "study hard!");
        sports = new Project(2L, "Sports", "stay healthy");

        it = new Category(1L, "IT", "informatics", "blue");
        friends = new Category(2L, "Friends", "fun!", "red");

        highpriority = new Priority(1L, "High Priority", "important", 1, false);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(new Project(1L, "School", "study hard!")));
        when(projectRepository.findById(2L)).thenReturn(Optional.of(new Project(2L, "Sports", "stay healthy")));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1L, "IT", "informatics", "blue")));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(new Category(2L, "Friends", "fun!", "red")));
        when(priorityRepository.findById(1L)).thenReturn(Optional.of(new Priority(1L, "High Priority", "important", 1, false)));

        javaBackend = new Task(1L, "java backend assignment", "eindopdracht voor het vak java", timestamp, "In Review", null, projectRepository.findById(1L).orElseThrow(), categoryRepository.findById(1L).orElseThrow(), priorityRepository.findById(1L).orElseThrow(), null);
        deployApi = new Task(4L, "deploy spring boot api", "deployen op oracle", timestamp, "To Do", null, projectRepository.findById(1L).orElseThrow(), categoryRepository.findById(1L).orElseThrow(), priorityRepository.findById(1L).orElseThrow(), null);
        crossPlatformDevelopment = new Task(2L, "cross platform assignment", "eindopdracht voor het vak cross platform", timestamp, "Ongoing", null, projectRepository.findById(1L).orElseThrow(), categoryRepository.findById(1L).orElseThrow(), priorityRepository.findById(1L).orElseThrow(), null);
        bowling = new Task(3L, "bowling avond", "leuk met vrienden", timestamp, "To Do", null, projectRepository.findById(2L).orElseThrow(), categoryRepository.findById(2L).orElseThrow(), priorityRepository.findById(1L).orElseThrow(), null);
        javaBackend.setChildtasks(Arrays.asList(deployApi));
        deployApi.setId(1L);
    }

    @Test
    void getAllTasks() throws Exception {
        List<Task> list = Arrays.asList(javaBackend, crossPlatformDevelopment, bowling);
        when(taskService.getAllTasks()).thenReturn(list);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(javaBackend.getId()))))
                .andExpect(jsonPath("$[0].childtasks[0].id", is(Math.toIntExact(deployApi.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(crossPlatformDevelopment.getId()))))
                .andExpect(jsonPath("$[2].id", is(Math.toIntExact(bowling.getId()))));
    }

    @Test
    void getTaskById() throws Exception {
        int taskId = 2;

        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(crossPlatformDevelopment));

        mvc.perform(get(baseUrl + "/" + taskId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(crossPlatformDevelopment.getId()))));
    }

    @Test
    void getTaskByIdNotFound() throws Exception {
        int taskId = 5;

        when(projectService.getProjectById(taskId)).thenReturn(Optional.empty());

        mvc.perform(get(baseUrl + "/" + taskId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskByProjectId() throws Exception {
        Long projectId = 2L;

        when(taskService.getTaskByProjectId(projectId)).thenReturn(Arrays.asList(javaBackend, crossPlatformDevelopment));

        mvc.perform(get(baseUrl + "/project/" + projectId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(javaBackend.getId()))))
                .andExpect(jsonPath("$[0].childtasks[0].id", is(Math.toIntExact(deployApi.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(crossPlatformDevelopment.getId()))));
    }

    @Test
    void getTaskByProjectIdNotFound() throws Exception {
        Long projectId = 5L;

        when(taskService.getTaskByProjectId(projectId)).thenReturn(List.of());

        mvc.perform(get(baseUrl + "/project/" + projectId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskByCategoryId() throws Exception {
        Long categoryId = 1L;

        when(taskService.getTaskByCategoryId(categoryId)).thenReturn(Arrays.asList(javaBackend, crossPlatformDevelopment));

        mvc.perform(get(baseUrl + "/category/" + categoryId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(javaBackend.getId()))))
                .andExpect(jsonPath("$[0].childtasks[0].id", is(Math.toIntExact(deployApi.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(crossPlatformDevelopment.getId()))));
    }

    @Test
    void createTask() throws Exception {
        Long projectId = 1L;
        Long categoryId = 1L;
        Long priorityId = 1L;

        Task iosStuderen = new Task();
        iosStuderen.setId(6L);
        iosStuderen.setTitle("ios examen studeren");
        iosStuderen.setDescription("veel werk");
        iosStuderen.setDeadline(timestamp);
        iosStuderen.setStatus("To Do");
        iosStuderen.setParenttaskid(null);
        iosStuderen.setProject(school);
        iosStuderen.setCategory(it);
        iosStuderen.setPriority(highpriority);
        iosStuderen.setChildtasks(null);

        when(taskService.saveTask(any(Task.class))).thenReturn(iosStuderen);
        when(projectService.getProjectById(Math.toIntExact(projectId))).thenReturn(Optional.of(school));
        when(categoryService.getCategoryById(Math.toIntExact(categoryId))).thenReturn(Optional.of(it));
        when(priorityService.getPriorityById(Math.toIntExact(priorityId))).thenReturn(Optional.of(highpriority));

        TaskRequest iosStuderenRequest = new TaskRequest(iosStuderen.getTitle(), iosStuderen.getDescription(), "2024-12-22 12:00:00", iosStuderen.getStatus(), 1, 1, 1, null);

        mvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iosStuderenRequest)))
                            .andExpect(status().isCreated())
                            .andExpect(header().string("location", "http://localhost/tasks/add/6"));
    }

}
