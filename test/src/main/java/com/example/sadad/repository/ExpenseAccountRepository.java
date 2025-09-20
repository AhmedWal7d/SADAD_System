package com.example.sadad.repository;

import com.example.sadad.entity.ExpenseAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseAccountRepository extends JpaRepository<ExpenseAccount, String> {
}
