package be.vives.ti.CheckIt.dto.response;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        String color
) { }
