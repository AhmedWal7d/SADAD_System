package com.example.sadad.repository;

import com.example.sadad.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT o FROM Organization o " +
            "WHERE UPPER(TRIM(o.organizationCode)) = UPPER(TRIM(:organizationCode))")
    Optional<Organization> findByOrganizationCodeSafe(@Param("organizationCode") String organizationCode);
}
