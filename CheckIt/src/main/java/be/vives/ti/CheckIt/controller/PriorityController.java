package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @GetMapping("/priorities")
    public List<Priority> getAllPriorities() {
        return priorityService.getAllPriorities();
    }
}
