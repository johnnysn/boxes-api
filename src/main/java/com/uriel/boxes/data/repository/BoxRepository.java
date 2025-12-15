package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

    List<Box> findAllByUser(User user, Sort sort);

    @Query("""
    select b
    from Box b
    where b.parent is null and b.user.id = :userId
    """)
    Page<Box> findByUserId(Long userId, Pageable pageable);

    boolean existsByUserAndId(User user, Long id);

    @Override
    @EntityGraph(attributePaths = {"user"})
    Optional<Box> findById(Long id);

}
