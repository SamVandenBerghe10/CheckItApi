package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/project/{id}")
    public List<Task> getTaskByProjectId(@PathVariable int id) {
        return taskService.getTaskByProjectId((long) id);
    }
}
