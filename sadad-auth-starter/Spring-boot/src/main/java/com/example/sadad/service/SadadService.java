package com.example.sadad.service;

import com.example.sadad.dto.CreateSadadRecordDto;
import com.example.sadad.model.SadadRecord;
import com.example.sadad.repository.SadadRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SadadService {
    private final SadadRecordRepository repo;

    public SadadService(SadadRecordRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public SadadRecord update(Long id, CreateSadadRecordDto dto, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sadad Record not found with id " + id));

        record.setOrganizationCode(dto.organizationCode());
        record.setOrganizationName(dto.organizationName());
        record.setLegalEntity(dto.legalEntity());
        record.setRemitterBank(dto.remitterBank());
        record.setRemitterBankAccount(dto.remitterBankAccount());
        record.setRemitterBankAccountName(dto.remitterBankAccountName());
        record.setBillerId(dto.billerId());
        record.setBillerName(dto.billerName());
        record.setVendorNumber(dto.vendorNumber());
        record.setVendorName(dto.vendorName());
        record.setVendorSiteCode(dto.vendorSiteCode());
        record.setInvoiceType(dto.invoiceType());
        record.setInvoiceNumber(dto.invoiceNumber());
        record.setSubscriptionAccountNumber(dto.subscriptionAccountNumber());
        record.setAmount(dto.amount().doubleValue());
        record.setExpenseAccountCode(dto.expenseAccountCode());
        record.setExpenseAccountName(dto.expenseAccountName());
        record.setBusinessCode(dto.businessCode());
        record.setBusinessName(dto.businessName());
        record.setLocationCode(dto.locationCode());
        record.setLocationName(dto.locationName());
        record.setStatus("UPDATED");
        record.setCreatedByUsername(username);

        return repo.save(record);
    }

    @Transactional
    public SadadRecord create(CreateSadadRecordDto dto, String createdByUsername) {
        SadadRecord r = new SadadRecord();
        r.setOrganizationCode(dto.organizationCode());
        r.setOrganizationName(dto.organizationName());
        r.setLegalEntity(dto.legalEntity());
        r.setRemitterBank(dto.remitterBank());
        r.setRemitterBankAccount(dto.remitterBankAccount());
        r.setRemitterBankAccountName(dto.remitterBankAccountName());
        r.setBillerId(dto.billerId());
        r.setBillerName(dto.billerName());
        r.setVendorNumber(dto.vendorNumber());
        r.setVendorName(dto.vendorName());
        r.setVendorSiteCode(dto.vendorSiteCode());
        r.setInvoiceType(dto.invoiceType());
        r.setInvoiceNumber(dto.invoiceNumber());
        r.setSubscriptionAccountNumber(dto.subscriptionAccountNumber());
        r.setAmount(dto.amount().doubleValue());
        r.setExpenseAccountCode(dto.expenseAccountCode());
        r.setExpenseAccountName(dto.expenseAccountName());
        r.setBusinessCode(dto.businessCode());
        r.setBusinessName(dto.businessName());
        r.setLocationCode(dto.locationCode());
        r.setLocationName(dto.locationName());
        r.setStatus("SAVED");
        r.setCreatedByUsername(createdByUsername);

        return repo.save(r);
    }

    @Transactional
    public SadadRecord cancel(Long id, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!"PENDING".equals(record.getStatus())) {
            throw new RuntimeException("Only pending records can be canceled");
        }

        record.setStatus("CANCELED");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    @Transactional
    public SadadRecord approve(Long id, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!"PENDING".equals(record.getStatus())) {
            throw new RuntimeException("Only pending records can be approved");
        }

        record.setStatus("APPROVED");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    @Transactional
    public SadadRecord markPending(Long id, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!"SAVED".equals(record.getStatus()) && !"UPDATED".equals(record.getStatus())) {
            throw new RuntimeException("Only SAVED or UPDATED records can be marked as PENDING");
        }

        record.setStatus("PENDING");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    @Transactional
    public SadadRecord changeStatus(Long id, String newStatus, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setStatus(newStatus);
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    public List<SadadRecord> getAll(String role) {
        if ("ROLE_RELEASER".equals(role)) {
            return repo.findByStatus("APPROVED");
        }
        return repo.findAll();
    }

    public List<SadadRecord> getAll() {
        return repo.findAll();
    }

    public SadadRecord getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sadad Record not found"));
    }
    public List<SadadRecord> getAllByStatus(String status) {
        return repo.findByStatus(status);
    }

}
