package com.uriel.boxes.controller;

import com.uriel.boxes.dto.input.LoginDto;
import com.uriel.boxes.dto.input.UserInDto.UserInDto;
import com.uriel.boxes.dto.output.UserOutDto;
import com.uriel.boxes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<UserOutDto> signin(@RequestBody @Valid LoginDto loginData) {
        var token = service.signin(loginData.email(), loginData.password());

        return ResponseEntity.ok().body(
                new UserOutDto(loginData.email(),  token)
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<UserOutDto> signup(@RequestBody UserInDto data) {
        var user = service.signup(data.email(), data.password(),  data.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserOutDto(
                        user.getEmail(),
                        user.getName()
                )
        );
    }

}
