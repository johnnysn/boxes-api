package com.uriel.boxes.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 400)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Box parent;

    @OneToMany(mappedBy = "box", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy("name")
    private List<Item> items;
}
