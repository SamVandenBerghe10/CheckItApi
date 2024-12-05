package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.PriorityRepository;
import be.vives.ti.CheckIt.dao.model.Priority;
import be.vives.ti.CheckIt.dao.model.Task;
import be.vives.ti.CheckIt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PriorityService {
    @Autowired
    private PriorityRepository priorityRepository;

    public List<Priority> getAllPriorities() {
        return priorityRepository.findAllByOrderBySequenceAsc();
    }

    public Optional<Priority> getPriorityById(int id) {
        return priorityRepository.findById((long)id);
    }

    public List<Priority> findByStandardpriorityIsTrue() {
        return priorityRepository.findByStandardpriorityIsTrue();
    }

    public List<Priority> getAllPrioritySorted() {
        return priorityRepository.findAll(Sort.by(Sort.Order.desc("standardpriority"), Sort.Order.asc("sequence")));
    }

    public Priority setStandardPriority(int id)
    {
        try {
            List<Priority> standardPriority = findByStandardpriorityIsTrue();
            for(Priority p : standardPriority){
                p.setStandardpriority(false);
                priorityRepository.save(p);
            }
        } catch (NoSuchElementException e) {}

        Priority newStandard = getPriorityById(id).orElseThrow(() -> new ResourceNotFoundException("Priority: " + id));
        newStandard.setStandardpriority(true);
        return priorityRepository.save(newStandard);
    }

    public Priority save(Priority priority) {
        return priorityRepository.save(priority);
    }
}
