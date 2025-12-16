package com.uriel.boxes.dto.output;

import com.uriel.boxes.data.entity.Box;

import java.util.List;

public record BoxWithItemsOutDto(
        Long id,
        String name,
        String description,
        Box.Color color,
        List<ItemOutDto> items
) {
}
