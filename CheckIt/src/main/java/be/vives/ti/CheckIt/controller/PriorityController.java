package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dto.response.PriorityResponse;
import be.vives.ti.CheckIt.exception.ResourceNotFoundException;
import be.vives.ti.CheckIt.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/priorities")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @GetMapping
    public List<PriorityResponse> getAllPriorities() {
        List<PriorityResponse> priorityResponses = new ArrayList<>();
        for(Priority priority : priorityService.getAllPriorities()) {
            PriorityResponse priorityResponse = new PriorityResponse(
                    priority.getId(),
                    priority.getName(),
                    priority.getDescription(),
                    priority.getSequence(),
                    priority.getStandardpriority()
            );
            priorityResponses.add(priorityResponse);
        }
        return priorityResponses;
    }

    @GetMapping("/{id}")
    public PriorityResponse getPriorityById(@PathVariable int id) {
        Priority priority = priorityService.getPriorityById(id).orElseThrow(() -> new ResourceNotFoundException("Priority: " + id));
        return new PriorityResponse(
                priority.getId(),
                priority.getName(),
                priority.getDescription(),
                priority.getSequence(),
                priority.getStandardpriority()
        );
    }

    @GetMapping("/sorted")
    public List<PriorityResponse> getAllPrioritySorted() {
        List<PriorityResponse> priorityResponses = new ArrayList<>();
        for(Priority priority : priorityService.getAllPrioritySorted()) {
            PriorityResponse priorityResponse = new PriorityResponse(
                    priority.getId(),
                    priority.getName(),
                    priority.getDescription(),
                    priority.getSequence(),
                    priority.getStandardpriority()
            );
            priorityResponses.add(priorityResponse);
        }
        return priorityResponses;
    }

    @PutMapping("/standard/{id}")
    public ResponseEntity<PriorityResponse> setStandardPriority(@PathVariable int id) {
        Priority priority = priorityService.setStandardPriority(id);
        if(priority == null) {
            throw new ResourceNotFoundException("Priority: " + id);
        }
        PriorityResponse priorityResponse = new PriorityResponse(
                priority.getId(),
                priority.getName(),
                priority.getDescription(),
                priority.getSequence(),
                priority.getStandardpriority()
        );
        return ResponseEntity.ok(priorityResponse);
    }
}
