package com.uriel.boxes.dto.output;

import com.uriel.boxes.data.entity.Box;

import java.util.List;

public record BoxWithItemsOutDto(
        Long id,
        String label,
        String description,
        Box.Color color,
        List<ItemOutDto> items
) {
}
