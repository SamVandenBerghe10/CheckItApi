package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priorities")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @GetMapping
    public List<Priority> getAllPriorities() {
        return priorityService.getAllPriorities();
    }

    @GetMapping("/{id}")
    public Priority getPriorityById(@PathVariable int id) {
        return priorityService.getPriorityById(id).orElse(null);
    }

    @GetMapping("/sorted")
    public List<Priority> getAllPrioritySorted() {
        return priorityService.getAllPrioritySorted();
    }

    @PostMapping("/standard/{id}")
    public List<Priority> setStandardPriority(@PathVariable int id) {
        return priorityService.setStandardPriority(id);
    }
}
