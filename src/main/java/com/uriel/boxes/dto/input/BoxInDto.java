package com.uriel.boxes.dto.input;

import com.uriel.boxes.data.entity.Box;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoxInDto(
        @NotBlank(message = "O label não pode ser vazio")
        @Size(max = 50, message = "O label não pode ter mais que 50 caracteres")
        String label,
        @Size(max = 400, message = "A description não pode ter mais que 400 caracteres")
        String description,
        Box.Color color
) {
}
