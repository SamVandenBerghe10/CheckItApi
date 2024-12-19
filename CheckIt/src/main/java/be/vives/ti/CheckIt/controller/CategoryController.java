package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dto.request.CategoryRequest;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.dto.response.CategoryResponse;
import be.vives.ti.CheckIt.exception.ResourceNotFoundException;
import be.vives.ti.CheckIt.service.CategoryService;
import be.vives.ti.CheckIt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : categoryService.getAllCategories()) {
            CategoryResponse categoryResponse = new CategoryResponse(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getColor()
            );
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryId(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new ResourceNotFoundException("Category: "+ id));
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getColor()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        category.setColor(categoryRequest.color());
        Category savedCategory = categoryService.saveCategory(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.getCategoryById(id).orElseThrow(() -> new ResourceNotFoundException("Category: "+ id));
        try {
            List<Task> taskList = taskService.getTaskByCategoryIdWithChildren((long)id);
            for (Task task : taskList) {
                task.setCategory(null);
                taskService.saveTask(task);
            }
            categoryService.deleteCategory(id);
        } catch (EmptyResultDataAccessException e) {

        }
    }
}
