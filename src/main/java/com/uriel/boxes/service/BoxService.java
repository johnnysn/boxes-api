package com.uriel.boxes.service;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.data.entity.User;
import com.uriel.boxes.data.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
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
}
