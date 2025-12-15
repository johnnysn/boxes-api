package com.uriel.boxes.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {

    private final TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            var userData = (JWTUserData) auth.getPrincipal();
            String newToken = tokenHandler.generateToken(userData.userId(), userData.email(), userData.name());

            response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newToken);
        }

        filterChain.doFilter(request, response);
    }
}
