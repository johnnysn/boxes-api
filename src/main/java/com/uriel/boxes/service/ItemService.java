package com.uriel.boxes.service;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.data.repository.BoxRepository;
import com.uriel.boxes.data.repository.ItemRepository;
import com.uriel.boxes.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final BoxRepository boxRepository;

    public Item create(Long boxId, String name, String description) {
        var box = boxRepository.findById(boxId).orElseThrow(
                () -> new ResourceNotFoundException("Box não encontrada!")
        );

        var item = Item.builder()
                .name(name)
                .description(description)
                .box(box)
                .build();
        return itemRepository.save(item);
    }

    public List<Item> listFromBox(Long boxId) {
        return itemRepository.findByBoxId(boxId, Sort.by("name"));
    }
}
