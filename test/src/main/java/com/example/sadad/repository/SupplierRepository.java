package com.example.sadad.repository;

import com.example.sadad.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    List<Supplier> findByOrganizationCodeIgnoreCase(String organizationCode);
}
