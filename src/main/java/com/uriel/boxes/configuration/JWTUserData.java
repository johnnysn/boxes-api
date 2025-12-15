package com.uriel.boxes.configuration;

public record JWTUserData(
        Long userId,
        String email,
        String name
) {
}
