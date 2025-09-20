package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "REMITTER_BANK_ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemitterBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 60)
    private String accountNumber;

    @Column(name = "ACCOUNT_NAME", length = 200)
    private String accountName;

    @Column(name = "CURRENCY", length = 10)
    private String currency;

    @Column(name = "ACTIVE_FLAG", length = 1)
    private String activeFlag = "Y";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID", nullable = false)
    private RemitterBank bank;
}
