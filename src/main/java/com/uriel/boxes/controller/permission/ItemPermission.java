package com.uriel.boxes.controller.permission;

import com.uriel.boxes.data.repository.ItemRepository;
import com.uriel.boxes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("itemPermission")
@RequiredArgsConstructor
public class ItemPermission {

    private final UserService userService;
    private final ItemRepository repository;

    public boolean hasPermission(Long id) {
        var user = userService.getLoggedInUser();

        return repository.existsByBoxUserAndId(user, id);
    }

}
