package com.example.sadad.controller;

import com.example.sadad.dto.LookupDto;
import com.example.sadad.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseRepository expenseRepo;

    // Constructor Injection
    public ExpenseController(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @GetMapping("/expense-accounts")
    public List<LookupDto> expenseAccounts() {
        return expenseRepo.findAll().stream()
                .map(e -> new LookupDto(e.getAccCode(), e.getAccName()))
                .toList();
    }
}

