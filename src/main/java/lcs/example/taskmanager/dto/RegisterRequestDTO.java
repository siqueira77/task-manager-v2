package lcs.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDTO(

    @NotBlank(message = "O usuário é obrigatório e não pode estar em branco")
    String username,

    @NotBlank(message = "A senha é obrigatória e não pode estar em branco")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,}$",
        message = "A senha deve ter pelo menos 6 caracteres, incluindo 1 número e 1 caractere especial"
    )
    String password
) {}
