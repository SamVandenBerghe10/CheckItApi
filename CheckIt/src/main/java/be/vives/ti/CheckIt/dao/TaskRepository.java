package be.vives.ti.CheckIt.dao;

import be.vives.ti.CheckIt.dao.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProjectId(Long projectId);
}
