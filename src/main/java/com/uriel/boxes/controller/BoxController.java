package com.uriel.boxes.controller;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.dto.input.BoxInDto;
import com.uriel.boxes.dto.output.BoxOutDto;
import com.uriel.boxes.service.BoxService;
import com.uriel.boxes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<BoxOutDto>> getAllMyBoxes() {
        var user = userService.getLoggedInUser();

        return ResponseEntity.ok(
                boxService.listUserBoxes(user).stream()
                        .map(b -> new BoxOutDto(b.getId(), b.getName(), b.getDescription()))
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<BoxOutDto> create(@RequestBody @Valid BoxInDto data) {
        Box createdBox = boxService.create(userService.getLoggedInUser(), data.name(), data.description());

        return ResponseEntity.created(URI.create("/boxes/" + createdBox.getId()))
                .body(new BoxOutDto(createdBox.getId(), createdBox.getName(), createdBox.getDescription()));
    }
}
