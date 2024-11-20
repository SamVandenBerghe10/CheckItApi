package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
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
    public Priority getTaskById(@PathVariable int id) {
        return priorityService.getPriorityById(id).orElse(null);
    }

    @GetMapping("/standard")
    public List<Priority> findByStandardpriorityIsTrue() {
        return priorityService.findByStandardpriorityIsTrue();
    }

    @GetMapping("/notstandard")
    public List<Priority> findByStandardpriorityIsFalse() {
        return priorityService.findByStandardpriorityIsFalse();
    }
}
