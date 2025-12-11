package com.uriel.boxes.controller;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.dto.input.ItemInDto;
import com.uriel.boxes.dto.output.ItemOutDto;
import com.uriel.boxes.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;

    @PostMapping("/boxes/{boxId}/items")
    @PreAuthorize("@boxPermission.hasPermission(#boxId)")
    public ItemOutDto create(@PathVariable Long boxId, @RequestBody @Valid ItemInDto data) {
        Item item = service.create(boxId, data.name(), data.description());

        return new ItemOutDto(item.getId(), item.getName(), item.getDescription());
    }

    @GetMapping("/boxes/{boxId}/items")
    @PreAuthorize("@boxPermission.hasPermission(#boxId)")
    public List<ItemOutDto> getItems(@PathVariable Long boxId) {
        return service.listFromBox(boxId).stream()
                .map(i -> new ItemOutDto(i.getId(), i.getName(), i.getDescription()))
                .toList();
    }

}
