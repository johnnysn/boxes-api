package com.uriel.boxes.service.auth;

import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.InvitationCodeRepository;
import com.uriel.boxes.data.repository.UserRepository;
import com.uriel.boxes.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SignupService {

    private final UserRepository userRepository;
    private final InvitationCodeRepository invitationCodeRepository;
    private final PasswordEncoder passwordEncoder;

    User execute(String email, String password, String name, String invitationCode, String encryptionSalt) {
        checkExisting(email);
        checkInvitationCode(email, invitationCode);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEncryptionSalt(encryptionSalt);
        user.setName(name);

        return userRepository.save(user);
    }

    private void checkExisting(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw new BadRequestException("O email já existe.");
    }

    private void checkInvitationCode(String email, String invitationCode) {
        if (invitationCodeRepository.findByEmailAndCode(email, invitationCode).isEmpty())
            throw new BadRequestException("O código de convite é inválido.");
    }
}
