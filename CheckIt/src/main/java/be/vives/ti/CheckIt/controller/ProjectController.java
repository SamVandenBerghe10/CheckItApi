package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dto.request.ProjectRequest;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.service.ProjectService;
import be.vives.ti.CheckIt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable int id) {
        return projectService.getProjectById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProject(@PathVariable int id) {
        List<Task> taskList = taskService.getTaskByProjectId((long)id);
        for (Task task : taskList) {
            taskService.deleteTask(Math.toIntExact(task.getId()));
        }
        Project deleteProject = projectService.getProjectById(id).orElse(null);
        if(deleteProject != null) {
            projectService.deleteProject(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
