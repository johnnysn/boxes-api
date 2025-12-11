package com.uriel.boxes.dto.input;

import jakarta.validation.constraints.NotBlank;

public record ItemUpdateInDto(
        @NotBlank(message = "O name não pode ser vazio") String name,
        String description,
        Long boxId
) {
}
