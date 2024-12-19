package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.dao.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findByParenttaskidIsNull();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTaskByProjectId(Long projectId) {
        return taskRepository.findByProjectIdAndParenttaskidIsNull(projectId);
    }

    public List<Task> getTaskByCategoryId(Long projectId) {
        return taskRepository.findByCategoryIdAndParenttaskidIsNull(projectId);
    }

    public List<Task> getTaskByCategoryIdWithChildren(Long projectId) {
        return taskRepository.findByCategoryId(projectId);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
