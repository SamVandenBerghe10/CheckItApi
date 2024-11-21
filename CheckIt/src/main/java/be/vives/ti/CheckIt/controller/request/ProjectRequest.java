package be.vives.ti.CheckIt.controller.request;

import jakarta.validation.constraints.*;

public record ProjectRequest(
        @Size(max=255) @NotBlank
        String name,
        @Size(max=255)
        String description
) {
}
