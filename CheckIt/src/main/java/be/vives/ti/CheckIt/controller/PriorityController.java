package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/standard")
    public List<Priority> findByStandardpriorityIsTrue() {
        return priorityService.findByStandardpriorityIsTrue();
    }

    @GetMapping("/notstandard")
    public List<Priority> findByStandardpriorityIsFalse() {
        return priorityService.findByStandardpriorityIsFalse();
    }
}
