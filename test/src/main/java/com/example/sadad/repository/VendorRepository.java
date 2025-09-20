package com.example.sadad.repository;

import com.example.sadad.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface VendorRepository extends JpaRepository<Vendor, String> {
    List<Vendor> findByBiller_BillerId(String billerId);
    Optional<Vendor> findByVendorNumber(String vendorNumber);
}
