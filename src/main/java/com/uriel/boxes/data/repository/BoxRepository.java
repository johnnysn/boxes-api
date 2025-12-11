package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

    List<Box> findAllByUser(User user, Sort sort);

    @Query("""
    select b
    from Box b
    where b.parent is null and b.user = :user
    """)
    Page<Box> findByUser(User user, Pageable pageable);

    boolean existsByUserAndId(User user, Long id);

}
