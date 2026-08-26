package lcs.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

    @NotBlank(message = "O nome é obrigatório e não pode estar em branco")
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    String name
) {}
