package be.vives.ti.CheckIt.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="deadline")
    private Timestamp deadline;

    @Column(name="status", nullable=false)
    private String status;

    @Column(name="parenttaskid")
    private Integer parenttaskid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="projectid")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoryid")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="priorityid")
    private Priority priority;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="parenttaskid")
    private List<Task> childtasks;
}
