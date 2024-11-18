package be.vives.ti.CheckIt.dao;

import be.vives.ti.CheckIt.dao.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
