package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.controller.request.TaskRequest;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.service.CategoryService;
import be.vives.ti.CheckIt.service.PriorityService;
import be.vives.ti.CheckIt.service.ProjectService;
import be.vives.ti.CheckIt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PriorityService priorityService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id).orElse(null);
    }

    @GetMapping("/project/{id}")
    public List<Task> getTaskByProjectId(@PathVariable Integer id) {
        return taskService.getTaskByProjectId((long) id);
    }

    @GetMapping("/category/{id}")
    public List<Task> getTaskByCategoryId(@PathVariable Integer id) {
        return taskService.getTaskByCategoryId((long) id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody TaskRequest taskRequest, @PathVariable int id) throws ParseException {
        Task task = getTaskById(id);
        if(task != null)
        {
            task.setId((long)id);
            task.setTitle(taskRequest.title());
            task.setDescription(taskRequest.description());
            task.setDeadline(new Timestamp(dateFormat.parse(taskRequest.deadline()).getTime()));
            task.setStatus(taskRequest.status());

            if (taskRequest.categoryid() != null) {
                Category category = categoryService.getCategoryById(taskRequest.categoryid()) // Service or repository call
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                task.setCategory(category);
            }

            if (taskRequest.projectid() != null) {
                Project project = projectService.getProjectById(taskRequest.projectid())
                        .orElseThrow(() -> new RuntimeException("Project not found"));
                task.setProject(project);
            }

            if (taskRequest.priorityid() != null) {
                Priority priority = priorityService.getPriorityById(taskRequest.priorityid())
                        .orElseThrow(() -> new RuntimeException("Priority not found"));
                task.setPriority(priority);
            }
            task.setParenttaskid(taskRequest.parenttaskid());

            Task updatedTask = taskService.saveTask(task);
            return ResponseEntity.ok(updatedTask);
        }
        else {
            throw new RuntimeException("Task not found");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest) throws ParseException {
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDeadline(new Timestamp(dateFormat.parse(taskRequest.deadline()).getTime()));
        task.setStatus(taskRequest.status());

        if (taskRequest.categoryid() != null) {
            Category category = categoryService.getCategoryById(taskRequest.categoryid()) // Service or repository call
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }

        if (taskRequest.projectid() != null) {
            Project project = projectService.getProjectById(taskRequest.projectid())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        if (taskRequest.priorityid() != null) {
            Priority priority = priorityService.getPriorityById(taskRequest.priorityid())
                    .orElseThrow(() -> new RuntimeException("Priority not found"));
            task.setPriority(priority);
        }

       task.setParenttaskid(taskRequest.parenttaskid());
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        Task task = taskService.getTaskById(id).orElse(null);
        if(task != null)
        {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}
