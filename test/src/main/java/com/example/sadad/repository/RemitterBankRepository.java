package com.example.sadad.repository;

import com.example.sadad.entity.RemitterBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RemitterBankRepository extends JpaRepository<RemitterBank, String> {

    Optional<RemitterBank> findByBankCode(String bankCode);

    List<RemitterBank> findByOrganizationCode(String organizationCode);

    List<RemitterBank> findByOrganizationCodeIgnoreCase(String organizationCode);
}
