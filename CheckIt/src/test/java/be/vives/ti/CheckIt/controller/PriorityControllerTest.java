package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dto.request.ProjectRequest;
import be.vives.ti.CheckIt.service.PriorityService;
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

@WebMvcTest(controllers = PriorityController.class)
public class PriorityControllerTest {
    private final String baseUrl = "/priorities";

    @MockBean
    private PriorityService priorityService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private Priority highImportant;
    private Priority mediumImportant;
    private Priority lowImportant;

    @BeforeEach
    void setUp() {
        highImportant = new Priority(1L, "high priority", "very important", 1, false);
        mediumImportant = new Priority(2L, "medium priority", "medium", 2, false);
        lowImportant = new Priority(3L, "low priority", "low", 3, false);
    }

    @Test
    void getAllPriorities() throws Exception {
        List<Priority> list = Arrays.asList(mediumImportant, lowImportant, highImportant);
        when(priorityService.getAllPriorities()).thenReturn(list);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(mediumImportant.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(lowImportant.getId()))))
                .andExpect(jsonPath("$[2].id", is(Math.toIntExact(highImportant.getId()))));
    }

    @Test
    void getPriorityById() throws Exception {
        int priorityId = 2;

        when(priorityService.getPriorityById(priorityId)).thenReturn(Optional.of(mediumImportant));

        mvc.perform(get(baseUrl + "/" + priorityId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(mediumImportant.getId()))));
    }

    @Test
    void getPriorityByIdNotFound() throws Exception {
        int priorityId = 5;

        when(priorityService.getPriorityById(priorityId)).thenReturn(Optional.empty());

        mvc.perform(get(baseUrl + "/" + priorityId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPrioritySorted() throws Exception {
        List<Priority> list = Arrays.asList(highImportant, mediumImportant, lowImportant);
        when(priorityService.getAllPriorities()).thenReturn(list);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(highImportant.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(mediumImportant.getId()))))
                .andExpect(jsonPath("$[2].id", is(Math.toIntExact(lowImportant.getId()))));
    }

    //TODO setStandardPriority: PUT
}
