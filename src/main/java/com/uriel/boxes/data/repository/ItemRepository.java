package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.data.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBoxId(Long boxId, Sort sort);

    boolean existsByBoxUserAndId(User user, Long id);

    @Override
    @EntityGraph(attributePaths = {"box.user"})
    Optional<Item> findById(Long id);
}
