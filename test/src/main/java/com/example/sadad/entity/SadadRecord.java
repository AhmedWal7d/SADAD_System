package com.example.sadad.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sadad_records")
public class SadadRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organizationCode;
    private String organizationName;
    private String legalEntity;
    private String legalEntityName;

    private String remitterBank;
    private String remitterBankAccount;
    private String remitterBankAccountName;

    private String billerId;
    private String billerName;

    private String vendorNumber;
    private String vendorName;
    private String vendorSiteCode;

    private String invoiceType;
    private String invoiceNumber;
    private String subscriptionAccountNumber;

    private BigDecimal amount;

    private String expenseAccountCode;
    private String expenseAccountName;

    private String businessCode;
    private String businessName;

    private String locationCode;
    private String locationName;

    private String status = "SAVED"; // SAVED/APPROVED/RELEASED/CANCELLED

    private String createdByUsername; // من SecurityContext

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CostCenter> costCenters = new ArrayList<>();

    // ================= Getters & Setters =================
    public Long getId() { return id; }

    public String getOrganizationCode() { return organizationCode; }
    public void setOrganizationCode(String organizationCode) { this.organizationCode = organizationCode; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public String getLegalEntity() { return legalEntity; }
    public void setLegalEntity(String legalEntity) { this.legalEntity = legalEntity; }

    public String getLegalEntityName() { return legalEntityName; }
    public void setLegalEntityName(String legalEntityName) { this.legalEntityName = legalEntityName; }

    public String getRemitterBank() { return remitterBank; }
    public void setRemitterBank(String remitterBank) { this.remitterBank = remitterBank; }

    public String getRemitterBankAccount() { return remitterBankAccount; }
    public void setRemitterBankAccount(String remitterBankAccount) { this.remitterBankAccount = remitterBankAccount; }

    public String getRemitterBankAccountName() { return remitterBankAccountName; }
    public void setRemitterBankAccountName(String remitterBankAccountName) { this.remitterBankAccountName = remitterBankAccountName; }

    public String getBillerId() { return billerId; }
    public void setBillerId(String billerId) { this.billerId = billerId; }

    public String getBillerName() { return billerName; }
    public void setBillerName(String billerName) { this.billerName = billerName; }

    public String getVendorNumber() { return vendorNumber; }
    public void setVendorNumber(String vendorNumber) { this.vendorNumber = vendorNumber; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getVendorSiteCode() { return vendorSiteCode; }
    public void setVendorSiteCode(String vendorSiteCode) { this.vendorSiteCode = vendorSiteCode; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getSubscriptionAccountNumber() { return subscriptionAccountNumber; }
    public void setSubscriptionAccountNumber(String subscriptionAccountNumber) { this.subscriptionAccountNumber = subscriptionAccountNumber; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getExpenseAccountCode() { return expenseAccountCode; }
    public void setExpenseAccountCode(String expenseAccountCode) { this.expenseAccountCode = expenseAccountCode; }

    public String getExpenseAccountName() { return expenseAccountName; }
    public void setExpenseAccountName(String expenseAccountName) { this.expenseAccountName = expenseAccountName; }

    public String getBusinessCode() { return businessCode; }
    public void setBusinessCode(String businessCode) { this.businessCode = businessCode; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public List<CostCenter> getCostCenters() { return costCenters; }
    public void setCostCenters(List<CostCenter> costCenters) { this.costCenters = costCenters; }
}
