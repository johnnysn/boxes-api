package com.uriel.boxes.service.auth;

import com.uriel.boxes.configuration.TokenHandler;
import com.uriel.boxes.data.repository.RefreshTokenRepository;
import com.uriel.boxes.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHandler tokenHandler;

    public String execute(String token) {
        var refreshToken = refreshTokenRepository.findByValidToken(UUID.fromString(token))
                .orElseThrow(() -> new ForbiddenException("Refresh token inválido."));

        return tokenHandler.generateToken(refreshToken.getUser());
    }

}
