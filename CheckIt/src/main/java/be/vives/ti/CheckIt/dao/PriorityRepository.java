package be.vives.ti.CheckIt.dao;

import be.vives.ti.CheckIt.dao.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
    List<Priority> findByStandardpriorityIsTrue();

    List<Priority> findAllByOrderBySequenceAsc();
}
