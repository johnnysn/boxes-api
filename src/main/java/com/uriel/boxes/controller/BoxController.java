package com.uriel.boxes.controller;

import com.uriel.boxes.controller.search.BoxSearchParams;
import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.dto.input.BoxInDto;
import com.uriel.boxes.dto.output.BoxOutDto;
import com.uriel.boxes.dto.output.BoxWithItemsOutDto;
import com.uriel.boxes.mapper.BoxMapper;
import com.uriel.boxes.service.box.BoxService;
import com.uriel.boxes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final UserService userService;
    private final BoxMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<BoxOutDto>> getAllMyBoxes() {
        var user = userService.getLoggedInUser();

        return ResponseEntity.ok(
                boxService.listUserBoxes(user).stream()
                        .map(mapper::entityToDto)
                        .toList()
        );
    }

    @GetMapping
    public Page<BoxWithItemsOutDto> findMyBoxes(@SortDefault(sort = "label") Pageable pageable, BoxSearchParams params) {
        var user = userService.getLoggedInUser();

        params.setUserId(user.getId());
        var boxes = boxService.search(params, pageable);
        return boxes.map(mapper::entityToDtoWithItems);
    }

    @PostMapping
    public ResponseEntity<BoxOutDto> create(@RequestBody @Valid BoxInDto data) {
        Box createdBox = boxService.create(userService.getLoggedInUser(), data.label(), data.description(), data.color());

        return ResponseEntity.created(URI.create("/boxes/" + createdBox.getId()))
                .body(mapper.entityToDto(createdBox));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@boxPermission.hasPermission(#id)")
    public BoxOutDto getById(@PathVariable Long id) {
        Box box =  boxService.getById(id);

        return new BoxOutDto(
            box.getId(), box.getLabel(), box.getDescription(), box.getColor()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("@boxPermission.hasPermission(#id)")
    public BoxOutDto getById(@PathVariable Long id, @RequestBody @Valid BoxInDto data) {
        Box boxData =  Box.builder()
                .label(data.label())
                .description(data.description())
                .id(id)
                .color(data.color())
                .build();

        Box box = boxService.update(boxData);

        return mapper.entityToDto(box);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@boxPermission.hasPermission(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boxService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
