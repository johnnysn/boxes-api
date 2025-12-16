package com.uriel.boxes.dto.input;

import com.uriel.boxes.data.entity.Box;
import jakarta.validation.constraints.NotBlank;

public record BoxInDto(
        @NotBlank(message = "O name não pode ser vazio") String name,
        String description,
        Box.Color color
) {
}
