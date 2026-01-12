package com.uriel.boxes.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uriel.boxes.data.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

import java.time.Instant;
import java.util.Optional;


@Component
public class TokenHandler {

    @Value("${app.security.secret}")
    private String secret;

    public String generateToken(User user) {
        return generateToken(user.getId(), user.getEmail(), user.getName());
    }

    public String generateToken(Long userId, String email, String name) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", userId)
                .withSubject(email)
                .withClaim("label", name)
                .withExpiresAt(Instant.now().plusSeconds(600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return Optional.of(
                    new JWTUserData(
                            jwt.getClaim("userId").asLong(),
                            jwt.getSubject(),
                            jwt.getClaim("label").asString()
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
