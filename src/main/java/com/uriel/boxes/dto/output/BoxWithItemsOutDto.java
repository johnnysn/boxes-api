package com.uriel.boxes.dto.output;

import java.util.List;

public record BoxWithItemsOutDto(
        Long id,
        String name,
        String description,
        List<ItemOutDto> items
) {
}
