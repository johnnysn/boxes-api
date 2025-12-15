package com.uriel.boxes.service;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.BoxRepository;
import com.uriel.boxes.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository repository;

    public List<Box> listUserBoxes(User user) {
        return repository.findAllByUser(user, Sort.by("name"));
    }

    public Page<Box> findByUser(User user, Pageable pageable) {
        return repository.findByUserId(user.getId(), pageable);
    }

    public Box create(User loggedInUser, String name, String description) {
        var box = Box.builder()
                .name(name)
                .description(description)
                .user(loggedInUser)
                .build();

        return repository.save(box);
    }

    public Box getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Box não foi encontrada para o ID especificado")
        );
    }

    public Box update(Box boxData) {
        var savedBox = getById(boxData.getId());

        savedBox.setName(boxData.getName());
        savedBox.setDescription(boxData.getDescription());

        return repository.save(savedBox);
    }
}
