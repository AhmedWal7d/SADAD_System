package com.example.sadad.repository;

import com.example.sadad.entity.SadadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SadadRecordRepository extends JpaRepository<SadadRecord, Long> {
    List<SadadRecord> findByStatus(String status);
    List<SadadRecord> findByStatusIn(List<String> statuses);
}
