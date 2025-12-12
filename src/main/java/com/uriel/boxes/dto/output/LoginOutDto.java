package com.uriel.boxes.dto.output;

public record LoginOutDto(
        String email,
        String name,
        String jwt,
        String refreshToken
) {
}
