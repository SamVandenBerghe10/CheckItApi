package be.vives.ti.CheckIt.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;

public record TaskRequest(

        @NotBlank @Size(max=255)
        String title,
        @Size(max=255)
        String description,
        String deadline,
        @NotBlank @Size(max=255)
        String status,
        @NotNull
        @Min(value = 0)
        Integer projectid,
        Integer categoryid,
        @Min(value = 0) @NotNull
        Integer priorityid,
        Integer parenttaskid

) {
}
