package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.ProjectRepository;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(int id) {
        return projectRepository.findById((long)id);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}
