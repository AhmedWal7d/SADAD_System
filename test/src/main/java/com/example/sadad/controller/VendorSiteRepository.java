package com.example.sadad.controller;

import com.example.sadad.entity.VendorSite;
import org.springframework.data.repository.Repository;

import java.util.Arrays;

interface VendorSiteRepository extends Repository<VendorSite, String> {
    Arrays findByVendor_VendorNumber(String vendorVendorNumber);
}
