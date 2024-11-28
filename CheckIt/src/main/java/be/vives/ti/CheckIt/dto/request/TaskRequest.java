package be.vives.ti.CheckIt.dto.request;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Null;

public record TaskRequest(

        @NotBlank @Size(max=255)
        String title,
        @Size(max=255)
        String description,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
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
