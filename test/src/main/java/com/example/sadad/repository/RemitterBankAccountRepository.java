package com.example.sadad.repository;

import com.example.sadad.entity.RemitterBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface RemitterBankAccountRepository extends JpaRepository<RemitterBankAccount, Long> {
    // 1️⃣ لجلب كل الحسابات حسب البنك
    List<RemitterBankAccount> findByBank_BankCode(String bankCode);

    // 2️⃣ لجلب الحساب الواحد حسب رقم الحساب (Read-only Name)
    Optional<RemitterBankAccount> findByAccountNumber(String accountNumber);
}


