package com.uriel.boxes.service.auth;

import com.uriel.boxes.configuration.TokenHandler;
import com.uriel.boxes.data.entity.RefreshToken;
import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.RefreshTokenRepository;
import com.uriel.boxes.dto.output.LoginOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
class SigninService {

    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginOutDto execute(String email, String password) {
        UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManager.authenticate(tokenAuth);

        User user = (User) auth.getPrincipal();
        String jwt = tokenHandler.generateToken(user);
        String refreshToken = createRefreshToken(user);

        return new LoginOutDto(
                user.getEmail(),
                user.getName(),
                jwt,
                refreshToken
        );
    }

    private String createRefreshToken(User user) {
        var refreshToken = RefreshToken.builder()
                .expiryDate(Instant.now().plus(Duration.ofHours(48)))
                .user(user)
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        return refreshTokenRepository.save(refreshToken).getToken().toString();
    }

}
