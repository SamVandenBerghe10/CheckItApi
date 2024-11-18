package be.vives.ti.CheckIt.dao;

import be.vives.ti.CheckIt.dao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
