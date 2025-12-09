package com.uriel.boxes.dto.input.UserInDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInDto(
        @Email(message = "Deve ser um email") String email,
        @NotBlank(message = "Não deve ser em branco") String name,
        @NotBlank(message = "Não deve ser em branco") String password
) {
}
