package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dto.request.TaskRequest;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.dto.response.TaskResponse;
import be.vives.ti.CheckIt.service.CategoryService;
import be.vives.ti.CheckIt.service.PriorityService;
import be.vives.ti.CheckIt.service.ProjectService;
import be.vives.ti.CheckIt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : taskService.getAllTasks()) {
            TaskResponse taskResponse = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus(),
                    task.getParenttaskid(),
                    task.getProject(),
                    task.getCategory(),
                    task.getPriority(),
                    task.getChildtasks()
            );
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable int id) {
        Task task = taskService.getTaskById(id).orElseThrow();
        TaskResponse taskResponse = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getStatus(),
                task.getParenttaskid(),
                task.getProject(),
                task.getCategory(),
                task.getPriority(),
                task.getChildtasks()
        );
        return taskResponse;
    }

    @GetMapping("/project/{id}")
    public List<TaskResponse> getTaskByProjectId(@PathVariable Integer id) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : taskService.getTaskByProjectId((long) id)) {
            TaskResponse taskResponse = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus(),
                    task.getParenttaskid(),
                    task.getProject(),
                    task.getCategory(),
                    task.getPriority(),
                    task.getChildtasks()
            );
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    @GetMapping("/category/{id}")
    public List<TaskResponse> getTaskByCategoryId(@PathVariable Integer id) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : taskService.getTaskByCategoryId((long) id)) {
            TaskResponse taskResponse = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus(),
                    task.getParenttaskid(),
                    task.getProject(),
                    task.getCategory(),
                    task.getPriority(),
                    task.getChildtasks()
            );
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createTask(@Valid @RequestBody TaskRequest taskRequest) throws ParseException {
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

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody TaskRequest taskRequest, @PathVariable int id) throws ParseException {
        Task task = taskService.getTaskById(id).orElseThrow();

        task.setId((long)id);
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDeadline(new Timestamp(dateFormat.parse(taskRequest.deadline()).getTime()));
        task.setStatus(taskRequest.status());
        if (taskRequest.categoryid() != null) {
            Category category = categoryService.getCategoryById(taskRequest.categoryid())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }
        else
        {
            task.setCategory(null);
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

        TaskResponse taskResponse = new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getDeadline(),
                updatedTask.getStatus(),
                updatedTask.getParenttaskid(),
                updatedTask.getProject(),
                updatedTask.getCategory(),
                updatedTask.getPriority(),
                updatedTask.getChildtasks()
        );

        return ResponseEntity.ok(taskResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTask(id);
        } catch (EmptyResultDataAccessException e) {

        }
    }
}
