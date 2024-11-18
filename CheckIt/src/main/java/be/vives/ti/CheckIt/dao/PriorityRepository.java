package be.vives.ti.CheckIt.dao;

import be.vives.ti.CheckIt.dao.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
