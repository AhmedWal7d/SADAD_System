package com.example.sadad.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LEGAL_ENTITY")
@Getter
@Setter
public class LegalEntity {

    @Id
    @Column(name = "ENTITY_CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LEGAL_ENTITY_NAME")
    private String legalEntityName;

    // Optional getters for clarity
    public String getEntityCode() { return code; }
    public String getEntityName() { return name; }
}
