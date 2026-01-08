package com.uriel.boxes.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotBlank(message = "O nome do item não pode ser vazio")
    private String name;

    @Column(length = 400)
    private String description;

    @Column(length = 24)
    private String iv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    @ToString.Exclude
    private Box box;
}
