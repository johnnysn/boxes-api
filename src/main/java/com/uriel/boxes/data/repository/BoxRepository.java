package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.data.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

    List<Box> findAllByUser(User user, Sort sort);

    boolean existsByUserAndId(User user, Long id);

}
