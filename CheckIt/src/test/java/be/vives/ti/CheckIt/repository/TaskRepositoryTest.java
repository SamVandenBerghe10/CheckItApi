package be.vives.ti.CheckIt.repository;

import be.vives.ti.CheckIt.dao.CategoryRepository;
import be.vives.ti.CheckIt.dao.PriorityRepository;
import be.vives.ti.CheckIt.dao.ProjectRepository;
import be.vives.ti.CheckIt.dao.TaskRepository;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2024-12-22 12:00:00", formatter);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        Project project = new Project(1L, "testproject", "testproject");
        projectRepository.save(project);
        Project project2 = new Project(2L, "testproject2", "testproject2");
        projectRepository.save(project2);
        Priority priority = new Priority(1L, "testpriority", "testpriority", 1, true);
        priorityRepository.save(priority);
        Priority priority2 = new Priority(2L, "testpriority2", "testpriority2", 2, false);
        priorityRepository.save(priority2);
        Category category = new Category(1L, "testcategory", "testcategory", "blue");
        categoryRepository.save(category);
        taskRepository.save(new Task(1L, "testtask", "testtask", timestamp, "To Do", null, project, category, priority, null));
        taskRepository.save(new Task(2L, "testtask2", "testtask2", timestamp, "Finished", 1, project, null, priority2, null));
        taskRepository.save(new Task(3L, "testtask3", "testtask3", timestamp, "Ongoing", null, project2, category, priority2, null));
    }

    @Test
    void findByParenttaskidIsNull() {
        List<Task> tasks = taskRepository.findByParenttaskidIsNull();
        assertThat(tasks.size()).isEqualTo(2);

        Task task = tasks.getFirst();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getParenttaskid()).isNull();
        Task task2 = tasks.getLast();
        assertThat(task2.getId()).isEqualTo(3L);
        assertThat(task2.getParenttaskid()).isNull();
    }

    @Test
    void findByProjectIdAndParenttaskidIsNull() {
        List<Task> tasks = taskRepository.findByProjectIdAndParenttaskidIsNull(1L);
        assertThat(tasks.size()).isEqualTo(1);

        Task task = tasks.getFirst();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getProject().getId()).isEqualTo(1L);
    }

    @Test
    void findByCategoryIdAndParenttaskidIsNull() {
        List<Task> tasks = taskRepository.findByCategoryIdAndParenttaskidIsNull(1L);
        assertThat(tasks.size()).isEqualTo(2);

        Task task = tasks.getFirst();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getCategory().getId()).isEqualTo(1L);

        Task task2 = tasks.getLast();
        assertThat(task2.getId()).isEqualTo(3L);
        assertThat(task2.getCategory().getId()).isEqualTo(1L);
    }

    @Test
    void findByCategoryId() {
        List<Task> tasks = taskRepository.findByCategoryId(1L);
        assertThat(tasks.size()).isEqualTo(2);

        Task task = tasks.getFirst();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getCategory().getId()).isEqualTo(1L);

        Task task2 = tasks.getLast();
        assertThat(task2.getId()).isEqualTo(3L);
        assertThat(task2.getCategory().getId()).isEqualTo(1L);
    }
}
