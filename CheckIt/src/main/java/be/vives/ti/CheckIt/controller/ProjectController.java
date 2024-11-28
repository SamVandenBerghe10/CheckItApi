package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dto.request.ProjectRequest;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.dto.response.ProjectResponse;
import be.vives.ti.CheckIt.exception.ResourceNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for(Project project : projectService.getAllProjects()) {
            ProjectResponse projectResponse = new ProjectResponse(
                    project.getId(),
                    project.getName(),
                    project.getDescription()
            );
            projectResponses.add(projectResponse);
        }
        return projectResponses;
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable int id) {
        Project project = projectService.getProjectById(id).orElseThrow(() -> new ResourceNotFoundException("Project: " + id));
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        Project savedProject = projectService.saveProject(project);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProject.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable int id) {
        projectService.getProjectById(id).orElseThrow(() -> new ResourceNotFoundException("Project: " + id));
        try{
            List<Task> taskList = taskService.getTaskByProjectId((long)id);
            for (Task task : taskList) {
                taskService.deleteTask(Math.toIntExact(task.getId()));
            }
            projectService.deleteProject(id);
        } catch (EmptyResultDataAccessException e) {

        }
    }
}
