package be.vives.ti.CheckIt.dto.request;

import jakarta.validation.constraints.*;

public record CategoryRequest(
        @NotBlank @Size(max=255)
        String name,
        @Size(max=255)
        String description,
        @NotBlank @Size(max=255)
        String color
) {
}
