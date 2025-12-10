package com.uriel.boxes.service;

import com.uriel.boxes.configuration.JWTUserData;
import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.UserRepository;
import com.uriel.boxes.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getLoggedInUser() {
        var userData = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return repository.findById(userData.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

}
