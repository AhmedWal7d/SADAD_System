package com.example.sadad.repository;

import com.example.sadad.entity.VendorSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorSiteRepository extends JpaRepository<VendorSite, String> {
      List<VendorSite> findByVendor_VendorNumber(String vendorNumber);
}
