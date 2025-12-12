package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.RefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("""
        select rt from RefreshToken rt
        where rt.token = :token
            and rt.expiryDate > current_timestamp
            and rt.revoked = false
    """)
    Optional<RefreshToken> findByValidToken(UUID token);
}
