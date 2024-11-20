package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.CategoryRepository;
import be.vives.ti.CheckIt.dao.model.Category;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById((long)id);
    }
}
