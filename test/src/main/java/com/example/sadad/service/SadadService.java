package com.example.sadad.service;

import com.example.sadad.dto.CreateSadadRecordDto;
import com.example.sadad.entity.*;
import com.example.sadad.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SadadService {

    private final SadadRecordRepository repo;
    private final OrganizationRepository orgRepo;
    private final RemitterBankRepository bankRepo;
    private final RemitterBankAccountRepository accountRepo;
    private final VendorRepository vendorRepo;
    private final BillerRepository billerRepo;
    private final ExpenseAccountRepository expenseRepo;
    private final BusinessUnitRepository businessRepo;
    private final LocationRepository locationRepo;
    private final CostCenterRepository costCenterRepo;
    private final SadadRecordCostCenterRepository sadadRecordCostCenterRepo;
    private final LegalEntityRepository legalEntityRepository;

    public SadadService(SadadRecordRepository repo,
                        OrganizationRepository orgRepo,
                        RemitterBankRepository bankRepo,
                        RemitterBankAccountRepository accountRepo,
                        VendorRepository vendorRepo,
                        BillerRepository billerRepo,
                        ExpenseAccountRepository expenseRepo,
                        BusinessUnitRepository businessRepo,
                        LocationRepository locationRepo,
                        CostCenterRepository costCenterRepo,
                        SadadRecordCostCenterRepository sadadRecordCostCenterRepo, LegalEntityRepository legalEntityRepository) {
        this.repo = repo;
        this.orgRepo = orgRepo;
        this.bankRepo = bankRepo;
        this.accountRepo = accountRepo;
        this.vendorRepo = vendorRepo;
        this.billerRepo = billerRepo;
        this.expenseRepo = expenseRepo;
        this.businessRepo = businessRepo;
        this.locationRepo = locationRepo;
        this.costCenterRepo = costCenterRepo;
        this.sadadRecordCostCenterRepo = sadadRecordCostCenterRepo;
        this.legalEntityRepository = legalEntityRepository;
    }

    @Transactional
    public SadadRecord create(CreateSadadRecordDto dto, String createdByUsername) {
        // ===== إنشاء سجل SadadRecord جديد =====
        SadadRecord r = new SadadRecord();

        // ===== Organization =====
        Organization org = orgRepo.findByOrganizationCodeSafe(dto.organizationCode())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        r.setOrganizationCode(org.getOrganizationCode());
        r.setOrganizationName(org.getOrganizationName());

        // ===== Legal Entity =====
        LegalEntity legalEntity = legalEntityRepository.findById(dto.legalEntity())
                .orElseThrow(() -> new RuntimeException("LegalEntity not found: " + dto.legalEntity()));
        r.setLegalEntity(legalEntity.getCode());
        r.setLegalEntityName(legalEntity.getName());

        // ===== Remitter Bank & Account =====
        r.setRemitterBank(dto.remitterBank() != null ? dto.remitterBank() : "UNKNOWN");
        r.setRemitterBankAccount(dto.remitterBankAccount() != null ? dto.remitterBankAccount() : "UNKNOWN");

        // ===== Biller =====
        r.setBillerId(dto.billerId() != null ? String.valueOf(dto.billerId()) : "UNKNOWN");

        // ===== Vendor =====
        r.setVendorNumber(dto.vendorNumber() != null ? dto.vendorNumber() : "UNKNOWN");
        r.setVendorSiteCode(dto.vendorSiteCode() != null ? dto.vendorSiteCode() : "UNKNOWN");

        // ===== Invoice =====
        r.setInvoiceType(dto.invoiceType() != null ? dto.invoiceType() : "UNKNOWN");
        r.setInvoiceNumber(dto.invoiceNumber() != null ? dto.invoiceNumber() : "UNKNOWN");
        r.setSubscriptionAccountNumber(dto.subscriptionAccountNumber() != null ? dto.subscriptionAccountNumber() : "UNKNOWN");

        // ===== Amount =====
        r.setAmount(dto.amount() != null ? dto.amount() : BigDecimal.ZERO);

        // ===== Expense Account =====
        r.setExpenseAccountCode(dto.expenseAccountCode() != null ? dto.expenseAccountCode() : "UNKNOWN");

        // ===== Business & Location =====
        r.setBusinessCode(dto.businessCode() != null ? dto.businessCode() : "UNKNOWN");
        r.setLocationCode(dto.locationCode() != null ? dto.locationCode() : "UNKNOWN");

        // ===== Status & audit =====
        r.setStatus("SAVED");
        r.setCreatedByUsername(createdByUsername);
        r.setCreatedAt(LocalDateTime.now());

        // ===== حفظ السجل أولاً لتجنب مشكلة TransientPropertyValueException =====
        r = repo.save(r);

        final SadadRecord finalRecord = r;
        List<CostCenter> costCenters = dto.costCenters().stream().map(dtoCc -> {
            CostCenter cc = new CostCenter(); // إنشاء جديد
            cc.setCostCenterCode(dtoCc.costCenterCode());
            cc.setPercentage(dtoCc.percentage());
            cc.setRecord(finalRecord); // ربط بالـ SadadRecord
            return cc;
        }).collect(Collectors.toList());

        finalRecord.setCostCenters(costCenters);


        // ===== تحقق مجموع percentages =====
        BigDecimal totalPercentage = costCenters.stream()
                .map(CostCenter::getPercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPercentage.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new RuntimeException("Total cost center percentages must equal 100%. Current total: " + totalPercentage);
        }

        // ===== ربط CostCenters بالـ SadadRecord =====
        finalRecord.setCostCenters(costCenters);

        // ===== إعادة حفظ السجل مع الأطفال =====
        return repo.save(finalRecord);
    }



    // ---------------- UPDATE ----------------
    @Transactional
    public SadadRecord update(Long id, CreateSadadRecordDto dto, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sadad Record not found with id " + id));

        Organization org = orgRepo.findByOrganizationCodeSafe(dto.organizationCode())
                .orElseThrow(() -> new RuntimeException("Organization not found"));


        record.setOrganizationCode(org.getOrganizationCode());
        record.setOrganizationName(org.getOrganizationName());
        LegalEntity legalEntity = legalEntityRepository.findById(dto.legalEntity())
                .orElseThrow(() -> new RuntimeException("LegalEntity not found: " + dto.legalEntity()));
        record.setLegalEntity(legalEntity.getCode());
        record.setLegalEntityName(legalEntity.getName());


        record.setStatus("UPDATED");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());

        return repo.save(record);
    }

    // ---------------- CANCEL ----------------
    @Transactional
    public SadadRecord cancel(Long id, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setStatus("CANCELED");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    // ---------------- APPROVE ----------------
    @Transactional
    public SadadRecord approve(Long id, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));


        record.setStatus("APPROVED");
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());

        return repo.save(record);
    }


    // ---------------- MARK PENDING ----------------
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
    public SadadRecord release(Long id, String username, boolean success) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!"APPROVED".equals(record.getStatus())) {
            throw new IllegalStateException("Only APPROVED records can be released");
        }

        if (success) {
            record.setStatus("COMPLETED");
        } else {
            record.setStatus("FAILED");
        }

        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }


    public List<SadadRecord> getAllByStatuses(List<String> statuses) {
        return repo.findByStatusIn(statuses);
    }




    // ---------------- CHANGE STATUS ----------------
    @Transactional
    public SadadRecord changeStatus(Long id, String newStatus, String username) {
        SadadRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setStatus(newStatus);
        record.setUpdatedBy(username);
        record.setUpdatedAt(LocalDateTime.now());
        return repo.save(record);
    }

    // ---------------- GETTERS ----------------
    public List<SadadRecord> getAll() {
        return repo.findAll();
    }

    public List<SadadRecord> getAllByStatus(String status) {
        return repo.findByStatus(status);
    }

    public SadadRecord getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sadad Record not found"));
    }
}
