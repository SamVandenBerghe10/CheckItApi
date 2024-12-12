package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dto.request.CategoryRequest;
import be.vives.ti.CheckIt.service.CategoryService;
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

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {
    private final String baseUrl = "/categories";

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private Category it;
    private Category spareTime;
    private Category gym;

    @BeforeEach
    void setUp() {
        it = new Category(1L, "IT", "informatics", "blue");
        spareTime = new Category(2L, "spare time", "if you have time to spare", "yellow");
        gym = new Category(3L, "gym", "workouts", "red");
    }

    @Test
    void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(it, spareTime, gym);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(it.getId()))))
                .andExpect(jsonPath("$[1].id", is(Math.toIntExact(spareTime.getId()))))
                .andExpect(jsonPath("$[2].id", is(Math.toIntExact(gym.getId()))));
    }

    @Test
    void getCategoryId() throws Exception {
        int categoryId = 2;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(spareTime));

        mvc.perform(get(baseUrl + "/" + categoryId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(spareTime.getId()))));
    }

    @Test
    void getCategoryIdNotFound() throws Exception {
        int categoryId = 5;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        mvc.perform(get(baseUrl + "/" + categoryId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addCategory() throws Exception {
        Category baking = new Category(4L, "Baking", "pies, cupcakes,...", "green");

        when(categoryService.saveCategory(any(Category.class))).thenReturn(baking);

        CategoryRequest bakingRequest = new CategoryRequest(baking.getName(), baking.getDescription(), baking.getColor());

        mvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bakingRequest)))
                            .andExpect(status().isCreated())
                            .andExpect(header().string("location", "http://localhost/categories/add/4"));
    }

    @Test
    void addCategoryValidationError() throws Exception {
        CategoryRequest bakingRequest = new CategoryRequest("", "", "");

        mvc.perform(post(baseUrl + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bakingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCategory() throws Exception {
        Long categoryId = 5L;

        Category category = new Category(categoryId, "skydiving", "falling from the sky", "gray");

        when(categoryService.getCategoryById(Math.toIntExact(categoryId))).thenReturn(Optional.of(category));

        mvc.perform(delete(baseUrl + "/delete/" + categoryId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCategoryNotFound() throws Exception {
        Long categoryId = 5L;

        when(categoryService.getCategoryById(Math.toIntExact(categoryId))).thenReturn(Optional.empty());

        mvc.perform(delete(baseUrl + "/delete/" + categoryId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
