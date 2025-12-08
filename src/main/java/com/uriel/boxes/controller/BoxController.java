package com.uriel.boxes.controller;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;

    @GetMapping
    public ResponseEntity<List<Box>> getMyBoxes() {
        return ResponseEntity.ok(
                boxService.listAll()
        );
    }
}
