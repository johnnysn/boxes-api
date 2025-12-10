package com.uriel.boxes.controller.permission;

import com.uriel.boxes.data.repository.BoxRepository;
import com.uriel.boxes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("boxPermission")
@RequiredArgsConstructor
public class BoxPermission {

    private final BoxRepository repository;
    private final UserService userService;

    public boolean hasPermission(Long id) {
        var user = userService.getLoggedInUser();

        return repository.existsByUserAndId(user, id);
    }
}
