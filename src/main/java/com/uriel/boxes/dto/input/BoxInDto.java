package com.uriel.boxes.dto.input;

import com.uriel.boxes.data.entity.Box;
import jakarta.validation.constraints.NotBlank;

public record BoxInDto(
        @NotBlank(message = "O label não pode ser vazio") String label,
        String description,
        Box.Color color
) {
}
