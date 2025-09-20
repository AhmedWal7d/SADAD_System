package com.example.sadad.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SADAD_ORGANIZATION")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EntityOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORG_ID")
    private Long id;

    @Column(name = "ORG_CODE", nullable = false, unique = true, length = 50)
    private String orgCode;

    @Column(name = "ORG_NAME", nullable = false, length = 255)
    private String orgName;

    @Column(name = "LEGAL_ENTITY_CODE", length = 50)
    private String legalEntityCode;

    @Column(name = "ACTIVE_FLAG", length = 1)
    private String activeFlag = "Y";
}
