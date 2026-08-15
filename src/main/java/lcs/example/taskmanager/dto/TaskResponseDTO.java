package lcs.example.taskmanager.dto;

public record TaskResponseDTO(
    Long id,
    String title,
    boolean completed,
    String projectName // Em vez de devolver o objeto Projeto inteiro, devolvemos só o nome!
) {}