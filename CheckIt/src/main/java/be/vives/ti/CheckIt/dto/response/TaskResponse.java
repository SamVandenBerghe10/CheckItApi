package be.vives.ti.CheckIt.dto.response;

import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.dao.model.Task;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Timestamp deadline,
        String status,
        Integer parenttaskid,
        Project project,
        Category category,
        Priority priority,
        List<Task> childtasks
) { }
