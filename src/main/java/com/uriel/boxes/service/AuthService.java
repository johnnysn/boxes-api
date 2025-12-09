package com.uriel.boxes.service;

import com.uriel.boxes.configuration.TokenHandler;
import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;

    public User signup(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        return userRepository.save(user);
    }

    public String signin(String email, String password) {
        UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManager.authenticate(tokenAuth);

        User user = (User) auth.getPrincipal();
        return tokenHandler.generateToken(user);
    }
}
