package be.vives.ti.CheckIt.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record PriorityRequest(
        Long id,
    String name,
    String description,
    Integer sequence,
    Boolean standardpriority
) {
}
