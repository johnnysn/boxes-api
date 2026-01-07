package com.uriel.boxes.controller;

import com.uriel.boxes.dto.input.LoginInDto;
import com.uriel.boxes.dto.input.RefreshTokenInDto;
import com.uriel.boxes.dto.input.UserInDto;
import com.uriel.boxes.dto.output.LoginOutDto;
import com.uriel.boxes.dto.output.UserOutDto;
import com.uriel.boxes.service.UserService;
import com.uriel.boxes.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final UserService userService;

    @PostMapping("/signin")
    public LoginOutDto signin(@RequestBody @Valid LoginInDto loginData) {
        return service.signin(loginData.email(), loginData.password());
    }

    @PostMapping("/token")
    public ResponseEntity<String> refreshToken(@RequestBody @Valid RefreshTokenInDto data) {
        return ResponseEntity.ok().body(
                service.refreshToken(data.token())
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<UserOutDto> signup(@RequestBody @Valid UserInDto data) {
        var user = service.signup(data.email(), data.password(),  data.name(), data.invitationCode(), data.encryptionSalt());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserOutDto(
                        user.getEmail(),
                        user.getName()
                )
        );
    }

    @GetMapping("/user-info")
    public UserOutDto userInfo() {
        var user = userService.getLoggedInUser();

        return new UserOutDto(
                user.getEmail(),
                user.getName()
        );
    }

    @GetMapping("/salt")
    public String getSalt(@RequestParam String email) {
        var user = userService.getByEmail(email);

        return user.getEncryptionSalt();
    }

}
