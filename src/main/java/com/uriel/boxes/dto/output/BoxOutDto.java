package com.uriel.boxes.dto.output;

import com.uriel.boxes.data.entity.Box;

public record BoxOutDto(
        Long id,
        String name,
        String description,
        Box.Color color
) {
}
