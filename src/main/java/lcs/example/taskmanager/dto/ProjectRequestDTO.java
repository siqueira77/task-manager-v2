package lcs.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(

    @NotBlank(message = "O título é obrigatório e não pode estar em branco")
    @Size(min = 3, message = "O título deve ter pelo menos 3 caracteres")
    String title,
      
    @NotNull(message = "Você deve informar a qual categoria esse projeto pertence")
    Long categoryId
) {} 

