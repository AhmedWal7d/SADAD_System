package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORGANIZATIONS")
@Getter
@Setter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    @Column(name = "ORGANIZATION_NAME")
    private String organizationName;
}
