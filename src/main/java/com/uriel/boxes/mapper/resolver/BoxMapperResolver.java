package com.uriel.boxes.mapper.resolver;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.service.box.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoxMapperResolver {

    private final BoxService service;

    public Box fromId(Long id) {
        if (id == null) return null;

        return service.getById(id);
    }

}
