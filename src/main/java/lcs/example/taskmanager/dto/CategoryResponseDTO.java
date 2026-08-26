package lcs.example.taskmanager.dto;

public record CategoryResponseDTO(
    Long id,
    String name,
    int projectCount
) {}
