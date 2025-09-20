package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "ROLES_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 50)
    private String name;
}
