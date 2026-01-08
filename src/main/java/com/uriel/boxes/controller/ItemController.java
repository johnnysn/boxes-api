package com.uriel.boxes.controller;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.dto.input.ItemInDto;
import com.uriel.boxes.dto.input.ItemUpdateInDto;
import com.uriel.boxes.dto.output.ItemOutDto;
import com.uriel.boxes.mapper.ItemMapper;
import com.uriel.boxes.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;
    private final ItemMapper mapper;

    @PostMapping("/boxes/{boxId}/items")
    @PreAuthorize("@boxPermission.hasPermission(#boxId)")
    public ResponseEntity<ItemOutDto> create(@PathVariable Long boxId, @RequestBody @Valid ItemInDto data) {
        Item item = service.create(boxId, data.name(), data.description(), data.iv());

        var output = new ItemOutDto(item.getId(), item.getName(), item.getDescription(), item.getIv());
        return ResponseEntity.created(URI.create("/items/" + item.getId())).body(output);
    }

    @GetMapping("/boxes/{boxId}/items")
    @PreAuthorize("@boxPermission.hasPermission(#boxId)")
    public List<ItemOutDto> getItems(@PathVariable Long boxId) {
        return service.listFromBox(boxId).stream()
                .map(i -> new ItemOutDto(i.getId(), i.getName(), i.getDescription(), i.getIv()))
                .toList();
    }

    @PutMapping("/items/{id}")
    @PreAuthorize("@itemPermission.hasPermission(#id)")
    public ItemOutDto update(@PathVariable Long id, @RequestBody @Valid ItemUpdateInDto data) {
        Item item = mapper.dtoToEntity(data);
        item.setId(id);
        Item updated = service.update(item);
        return mapper.entityToDto(updated);
    }

    @DeleteMapping("/items/{id}")
    @PreAuthorize("@itemPermission.hasPermission(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/{id}")
    @PreAuthorize("@itemPermission.hasPermission(#id)")
    public ItemOutDto getById(Long id) {
        return mapper.entityToDto(
                service.getById(id)
        );
    }

}
