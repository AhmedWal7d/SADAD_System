package com.example.sadad.repository;


import com.example.sadad.entity.OrgRemitterAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgRemitterAccountRepository extends JpaRepository<OrgRemitterAccount, Long> {
    List<OrgRemitterAccount> findByOrganization_Id(Long orgId);
}
