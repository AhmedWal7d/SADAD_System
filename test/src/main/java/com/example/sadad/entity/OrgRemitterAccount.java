package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "ORG_REMITTER_ACCOUNT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrgRemitterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = false)
    private EntityOrganization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private RemitterBankAccount account;

    @Column(name = "IS_DEFAULT", length = 1)
    private String isDefault = "N";
}
