package com.uriel.boxes.service;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.data.repository.BoxRepository;
import com.uriel.boxes.data.repository.ItemRepository;
import com.uriel.boxes.exception.BadRequestException;
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

    public Item create(Long boxId, String name, String description, String iv) {
        var box = boxRepository.findById(boxId).orElseThrow(
                () -> new ResourceNotFoundException("Box não encontrada!")
        );

        var item = Item.builder()
                .name(name)
                .description(description)
                .box(box)
                .iv(iv)
                .build();
        return itemRepository.save(item);
    }

    public List<Item> listFromBox(Long boxId) {
        return itemRepository.findByBoxId(boxId, Sort.by("label"));
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public Item getById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Item não encontrado para o ID especificado!")
        );
    }

    public Item update(Item item) {
        var saved = getById(item.getId());

        if (item.getBox() != null) {
            validarDonoDaBox(item, saved);
            saved.setBox(item.getBox());
        }

        saved.setName(item.getName());
        saved.setDescription(item.getDescription());
        saved.setIv(item.getIv());

        return itemRepository.save(saved);
    }

    private static void validarDonoDaBox(Item item, Item saved) {
        if (!saved.getBox().getUser().getId().equals(item.getBox().getUser().getId()))
            throw new BadRequestException("A box especificada pertence a outro usuário");
    }
}
