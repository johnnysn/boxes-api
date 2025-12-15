package com.uriel.boxes.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "invitation_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @NotBlank(message = "O código é obrigatório")
    private String code;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private LocalDateTime expirationDate;

    @NotBlank(message = "O email é obrigatório")
    @Email
    private String email;
}
