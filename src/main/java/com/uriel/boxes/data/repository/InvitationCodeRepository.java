package com.uriel.boxes.data.repository;

import com.uriel.boxes.data.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {

    @Query("""
        select ic from InvitationCode ic where
                ic.email = :email
                and ic.code = :invitationCode
                and current_timestamp < ic.expirationDate
        """)
    List<InvitationCode> findByEmailAndCode(String email, String invitationCode);
}
