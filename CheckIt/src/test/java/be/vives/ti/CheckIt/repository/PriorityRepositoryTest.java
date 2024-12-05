package be.vives.ti.CheckIt.repository;

import be.vives.ti.CheckIt.dao.PriorityRepository;
import be.vives.ti.CheckIt.dao.model.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PriorityRepositoryTest {

    @Autowired
    private PriorityRepository priorityRepository;

    @BeforeEach
    void setUp() {
        priorityRepository.save(new Priority(1L, "important", "dont skip", 2, true));
        priorityRepository.save(new Priority(2L, "little less important", "do it", 3, false));
        priorityRepository.save(new Priority(3L, "medium", "meh", 1, false));
    }

    @Test
    void findByStandardpriorityIsTrue() {

        List<Priority> standardPriorities = priorityRepository.findByStandardpriorityIsTrue();
        assertThat(standardPriorities.size()).isEqualTo(1);

        Priority standardPriority = standardPriorities.getFirst();
        assertThat(standardPriority.getId()).isEqualTo(1L);
        assertThat(standardPriority.getName()).isEqualTo("important");
        assertThat(standardPriority.getDescription()).isEqualTo("dont skip");
        assertThat(standardPriority.getSequence()).isEqualTo(2);
        assertThat(standardPriority.getStandardpriority()).isEqualTo(true);
    }

    @Test
    void findByStandardpriorityIsFalse() {
        List<Priority> standardPriorities = priorityRepository.findByStandardpriorityIsFalse();
        assertThat(standardPriorities.size()).isEqualTo(2);

        Priority standardPriority = standardPriorities.getFirst();
        assertThat(standardPriority.getId()).isEqualTo(2L);
        assertThat(standardPriority.getName()).isEqualTo("little less important");
        assertThat(standardPriority.getDescription()).isEqualTo("do it");
        assertThat(standardPriority.getSequence()).isEqualTo(3);
        assertThat(standardPriority.getStandardpriority()).isEqualTo(false);
    }

    @Test
    void findAllByOrderBySequenceAsc() {
        List<Priority> standardPriorities = priorityRepository.findAllByOrderBySequenceAsc();
        assertThat(standardPriorities.size()).isEqualTo(3);

        Priority standardPriority = standardPriorities.getFirst();
        assertThat(standardPriority.getId()).isEqualTo(3L);
        assertThat(standardPriority.getName()).isEqualTo("medium");
        assertThat(standardPriority.getDescription()).isEqualTo("meh");
        assertThat(standardPriority.getSequence()).isEqualTo(1);
        assertThat(standardPriority.getStandardpriority()).isEqualTo(false);
    }
}
