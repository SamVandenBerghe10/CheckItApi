package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.ProjectRepository;
import be.vives.ti.CheckIt.dao.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
