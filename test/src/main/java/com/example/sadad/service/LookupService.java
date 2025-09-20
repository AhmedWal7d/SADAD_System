package com.example.sadad.service;

import com.example.sadad.entity.Organization;
import com.example.sadad.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LookupService {

    private final OrganizationRepository organizationRepository;

    public LookupService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // ================================
    // 1. Lists
    // ================================
    public List<Map<String, String>> getOrganizations() {
        // هنا ممكن تحوّل الـ entities لـ Map لو عاوز
        return organizationRepository.findAll()
                .stream()
                .map(org -> Map.of(
                        "code", org.getOrganizationCode(),
                        "name", org.getOrganizationName()
                ))
                .toList();
    }

    public List<Map<String, Object>> getLegalEntities() { return List.of(); }
    public List<Map<String, Object>> getBanks() { return List.of(); }
    public List<Map<String, Object>> getAccounts(String bankCode) { return List.of(); }
    public List<Map<String, Object>> getBillers() { return List.of(); }
    public List<Map<String, Object>> getVendors(Long billerId) { return List.of(); }
    public List<Map<String, Object>> getVendorSites(String vendorNumber) { return List.of(); }
    public List<Map<String, Object>> getExpenseAccounts() { return List.of(); }
    public List<Map<String, Object>> getBusinessUnits() { return List.of(); }
    public List<Map<String, Object>> getLocations() { return List.of(); }
    public List<Map<String, Object>> getCostCenters() { return List.of(); }

    // ================================
    // 2. Single lookups by code/id
    // ================================
    public String getOrganizationName(String code) {
        return organizationRepository.findByOrganizationCodeSafe(code)

                .map(Organization::getOrganizationName)
                .orElse("Organization not found for code: " + code);
    }

    public String getBankAccountName(String accountId) {
        return "Bank Account Name for " + accountId;
    }

    public String getBillerName(Long billerId) {
        return "Biller Name for " + billerId;
    }

    public String getVendorName(String vendorNumber) {
        return "Vendor Name for " + vendorNumber;
    }

    public String getExpenseAccountName(String accountCode) {
        return "Expense Account Name for " + accountCode;
    }

    public String getBusinessName(String businessCode) {
        return "Business Name for " + businessCode;
    }

    public String getLocationName(String locationCode) {
        return "Location Name for " + locationCode;
    }

    public String getCostCenterDescription(String costCenterCode) {
        return "Cost Center Description for " + costCenterCode;
    }
}
