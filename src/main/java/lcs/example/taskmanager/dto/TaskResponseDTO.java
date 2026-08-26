package lcs.example.taskmanager.dto;

public record TaskResponseDTO(
    Long id,
    String title,
    boolean completed,
    String projectName 
) {}