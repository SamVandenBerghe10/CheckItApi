package be.vives.ti.CheckIt.service;

import be.vives.ti.CheckIt.dao.PriorityRepository;
import be.vives.ti.CheckIt.dao.model.Priority;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PriorityServiceTest {

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private PriorityService priorityService;

    @Test
    void setStandardPriority() {
        Priority priority = new Priority(1L, "very important", "do asap", 1, true);
        priorityService.save(priority);
        Priority priority2 = new Priority(2L, "very important2", "do asap2", 2, false);
        priorityService.save(priority2);

        Priority newStandard = priorityService.setStandardPriority(2);
        assertThat(newStandard.getStandardpriority()).isEqualTo(true);

        Priority oldStandard = priorityService.getPriorityById(1).get();
        assertThat(oldStandard.getStandardpriority()).isEqualTo(false);

    }
}
