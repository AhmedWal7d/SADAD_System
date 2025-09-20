package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EXPENSE_ACCOUNTS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExpenseAccount {
    @Id
    @Column(name = "ACC_CODE")
    private String accCode;

    @Column(name = "ACC_NAME")
    private String accName;
}
