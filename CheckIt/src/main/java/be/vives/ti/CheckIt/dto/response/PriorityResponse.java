package be.vives.ti.CheckIt.dto.response;

public record PriorityResponse(
        Long id,
        String name,
        String description,
        Integer sequence,
        Boolean standardPriority
) { }
