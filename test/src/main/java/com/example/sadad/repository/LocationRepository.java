package com.example.sadad.repository;

import com.example.sadad.entity.LocationRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationRef, String> {
}
