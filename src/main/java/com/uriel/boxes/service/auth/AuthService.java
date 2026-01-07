package com.uriel.boxes.service.auth;

import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.UserRepository;
import com.uriel.boxes.dto.output.LoginOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SignupService signupService;
    private final SigninService signinService;
    private final RefreshTokenService refreshTokenService;

    public User signup(String email, String password, String name, String invitationCode, String encryptionSalt) {
        return signupService.execute(email, password, name, invitationCode, encryptionSalt);
    }

    public LoginOutDto signin(String email, String password) {
        return signinService.execute(email, password);
    }

    public String refreshToken(String token) {
        return refreshTokenService.execute(token);
    }
}
