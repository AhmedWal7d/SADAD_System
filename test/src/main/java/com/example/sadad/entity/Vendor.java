package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VENDORS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
    @Column(name = "VENDOR_NUMBER")
    private String vendorNumber;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @ManyToOne
    @JoinColumn(name = "BILLER_ID", referencedColumnName = "billerId")  // نربط على billerId مش id
    private Biller biller;
}
