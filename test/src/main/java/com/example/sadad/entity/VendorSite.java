package com.example.sadad.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VENDOR_SITES")
@Getter
@Setter
public class VendorSite {
    @Id
    @Column(name = "SITE_CODE")
    private String siteCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDOR_NUMBER")
    private Vendor vendor;
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
