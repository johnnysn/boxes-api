package com.uriel.boxes.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizedHeader = request.getHeader("Authorization");

        if (Strings.isNotEmpty(authorizedHeader) &&  authorizedHeader.startsWith("Bearer ")) {
            System.out.println("HERE");
            String token = authorizedHeader.substring("Bearer ".length());

            Optional<JWTUserData> userDataOpt = tokenHandler.validateToken(token);

            if (userDataOpt.isPresent()) {
                System.out.println("HERE");
                var data = userDataOpt.get();
                System.out.println("" + data);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(data, null, List.of(() -> "USER"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
