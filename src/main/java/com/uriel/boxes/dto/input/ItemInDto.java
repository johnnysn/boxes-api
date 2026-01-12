package com.uriel.boxes.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ItemInDto(
        @NotBlank(message = "O label não pode ser vazio")
        @Size(max = 100, message = "O label deve ter, no máximo, 100 caracteres")
        String name,
        String description,
        @Size(max = 24, message = "O iv deve ter, no máximo, 24 caracteres") String iv
) {
}
