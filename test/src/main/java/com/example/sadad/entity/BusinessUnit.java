package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUSINESS_UNITS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessUnit {
    @Id
    @Column(name = "BUSINESS_CODE")
    private String businessCode;

    @Column(name = "BUSINESS_NAME")
    private String businessName;
}
