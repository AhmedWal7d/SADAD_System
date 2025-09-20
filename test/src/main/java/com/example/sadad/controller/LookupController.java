package com.example.sadad.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sadad.service.LookupService;

@RestController
@RequestMapping("/api/lookups")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    // 1. Get Organization Name by Code
    @GetMapping("/organizations/{code}")
    public ResponseEntity<?> getOrganizationName(@PathVariable("code") String code) {
        return ResponseEntity.ok(lookupService.getOrganizationName(code));
    }

    // 2. Get Bank Account Name by Account Id
    @GetMapping("/bank-accounts/{accountId}/name")
    public ResponseEntity<?> getBankAccountName(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(lookupService.getBankAccountName(accountId));
    }

    // 3. Get Biller Name by Biller Id
    @GetMapping("/billers/{billerId}/name")
    public ResponseEntity<?> getBillerName(@PathVariable("billerId") Long billerId) {
        return ResponseEntity.ok(lookupService.getBillerName(billerId));
    }

    // 4. Get Vendor Name by Vendor Number
    @GetMapping("/vendors/{vendorNumber}/name")
    public ResponseEntity<?> getVendorName(@PathVariable("vendorNumber") String vendorNumber) {
        return ResponseEntity.ok(lookupService.getVendorName(vendorNumber));
    }

    // 5. Get Expense Account Name by Code
    @GetMapping("/expense-accounts/{accountCode}/name")
    public ResponseEntity<?> getExpenseAccountName(@PathVariable("accountCode") String accountCode) {
        return ResponseEntity.ok(lookupService.getExpenseAccountName(accountCode));
    }

    // 6. Get Business Unit Name by Code
    @GetMapping("/business-units/{businessCode}/name")
    public ResponseEntity<?> getBusinessName(@PathVariable("businessCode") String businessCode) {
        return ResponseEntity.ok(lookupService.getBusinessName(businessCode));
    }

    // 7. Get Location Name by Code
    @GetMapping("/locations/{locationCode}/name")
    public ResponseEntity<?> getLocationName(@PathVariable("locationCode") String locationCode) {
        return ResponseEntity.ok(lookupService.getLocationName(locationCode));
    }

    // 8. Get Cost Center Description by Code
    @GetMapping("/cost-centers/{costCenterCode}/description")
    public ResponseEntity<?> getCostCenterDescription(@PathVariable("costCenterCode") String costCenterCode) {
        return ResponseEntity.ok(lookupService.getCostCenterDescription(costCenterCode));
    }
}
