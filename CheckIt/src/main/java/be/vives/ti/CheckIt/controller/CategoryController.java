package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.controller.request.CategoryRequest;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.service.CategoryService;
import be.vives.ti.CheckIt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryId(@PathVariable int id) {
        return categoryService.getCategoryById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        category.setColor(categoryRequest.color());
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id) {
        List<Task> taskList = taskService.getTaskByCategoryId((long)id);
        for (Task task : taskList) {
            task.setCategory(null);
            taskService.saveTask(task);
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
