package com.uriel.boxes.service.auth;

import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.UserRepository;
import com.uriel.boxes.dto.output.LoginOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SigninService signinService;
    private final RefreshTokenService refreshTokenService;

    public User signup(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        return userRepository.save(user);
    }

    public LoginOutDto signin(String email, String password) {
        return signinService.execute(email, password);
    }

    public String refreshToken(String token) {
        return refreshTokenService.execute(token);
    }
}
