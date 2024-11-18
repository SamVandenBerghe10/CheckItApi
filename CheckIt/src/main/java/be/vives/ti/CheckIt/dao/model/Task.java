package be.vives.ti.CheckIt.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    @Column(name="projectid", nullable=false, insertable=false, updatable=false)
    private Long projectid;

    @Column(name="categoryid", insertable=false, updatable=false)
    private Long categoryid;

    @Column(name="priorityid", nullable=false, insertable=false, updatable=false)
    private Long priorityid;

    @Column(name="parenttaskid", insertable=false, updatable=false)
    private Long parenttaskid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="projectid")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoryid")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="priorityid")
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parenttaskid")
    private Task parentTask;
}
