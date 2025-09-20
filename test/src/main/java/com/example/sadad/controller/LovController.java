package com.example.sadad.controller;
import com.example.sadad.dto.BillerDto;
import com.example.sadad.entity.RemitterBank;
import com.example.sadad.repository.VendorSiteRepository;

import com.example.sadad.dto.LookupDto;
import com.example.sadad.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/lov")
@RequiredArgsConstructor
public class LovController {
    private final OrganizationRepository orgRepo;
    private final RemitterBankRepository bankRepo;
    private final RemitterBankAccountRepository accountRepo;
    private final VendorSiteRepository vendorSiteRepository;
    private final ExpenseAccountRepository expenseAccountRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final LocationRepository locationRepository;
    private final CostCenterRepository costCenterRepository;
        private final BillerRepository billerRepository;
        private final LegalEntityRepository legalEntityRepository;
        private final VendorRepository vendorRepository;
    private final SupplierRepository supplierRepository;

    @GetMapping("/organizations")
    public List<LookupDto> organizations() {
        return orgRepo.findAll()
                .stream()
                .map(o -> new LookupDto(o.getOrganizationCode(), o.getOrganizationName()))
                .toList();
    }






    @GetMapping("/banks")
    public List<LookupDto> banks(
            @RequestParam(name = "organizationCode", required = false) String organizationCode) {

        if (organizationCode == null || organizationCode.isBlank()) {
            return bankRepo.findAll().stream()
                    .map(b -> new LookupDto(b.getBankCode(), b.getBankName()))
                    .toList();
        }

        List<RemitterBank> banks = bankRepo.findByOrganizationCodeIgnoreCase(organizationCode.trim());
        System.out.println("Fetched Banks = " + banks.size());

        return banks.stream()
                .map(b -> new LookupDto(b.getBankCode(), b.getBankName()))
                .toList();
    }


    @GetMapping("/bank-accounts")
    public List<LookupDto> bankAccounts(@RequestParam("bankCode") String bankCode) {
        return accountRepo.findByBank_BankCode(bankCode).stream()
                .map(a -> new LookupDto(a.getAccountNumber(), a.getAccountName()))
                .toList();
    }


    @GetMapping("/expense-accounts")
    public List<LookupDto> expenseAccounts() {
        return expenseAccountRepository.findAll().stream()
                .map(e -> new LookupDto(e.getAccCode(), e.getAccName()))
                .toList();
    }
    @GetMapping("/bank-account-name")
    public LookupDto bankAccountName(@RequestParam("accountNumber") String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .map(a -> new LookupDto(a.getAccountNumber(), a.getAccountName()))
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }


    @GetMapping("/locations")
    public List<LookupDto> locations() {
        return locationRepository.findAll().stream()
                .map(l -> new LookupDto(l.getLocationCode(), l.getLocationName()))
                .toList();
    }


    @GetMapping("/business-units")
    public List<LookupDto> businessUnits() {
        return businessUnitRepository.findAll().stream()
                .map(b -> new LookupDto(b.getBusinessCode(), b.getBusinessName()))
                .toList();
    }
    @GetMapping("/cost-centers")
    public List<LookupDto> costCenters() {
        return costCenterRepository.findAll().stream()
                .map(c -> new LookupDto(c.getCostCenterCode(), c.getCostCenterDesc()))
                .toList();
    }

    @GetMapping("/billers")
    public List<BillerDto> billers() {
        return billerRepository.findAll().stream()
                .map(b -> new BillerDto(b.getBillerId(), b.getBillerName()))
                .toList();
    }


    @GetMapping("/vendor-sites")
    public List<LookupDto> vendorSites(@RequestParam("vendorNumber") String vendorNumber) {
        return vendorSiteRepository.findByVendor_VendorNumber(vendorNumber).stream()
                .map(s -> new LookupDto(s.getSiteCode(), s.getSiteCode()))
                .toList();
    }


    @GetMapping("/legal-entities")
    public List<LookupDto> legalEntities() {
        return legalEntityRepository.findAll().stream()
                .map(le -> new LookupDto(le.getEntityCode(), le.getEntityName()))
                .toList();
    }

    @GetMapping("/vendors")
    public List<LookupDto> vendors(@RequestParam(name = "billerId") String billerId) {
        return vendorRepository.findByBiller_BillerId(billerId).stream()
                .map(v -> new LookupDto(v.getVendorNumber(), v.getVendorName()))
                .toList();
    }


    @GetMapping("/vendor-name")
    public LookupDto getVendorName(@RequestParam(name = "vendorNumber") String vendorNumber) {
        return vendorRepository.findByVendorNumber(vendorNumber)
                .map(v -> new LookupDto(v.getVendorNumber(), v.getVendorName()))
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }


    @GetMapping("/suppliers")
    public List<LookupDto> suppliers(
            @RequestParam(name = "organizationCode", required = false) String organizationCode) {

        if (organizationCode == null || organizationCode.isBlank()) {
            return supplierRepository.findAll().stream()
                    .map(s -> new LookupDto(s.getSupplierCode(), s.getSupplierName()))
                    .toList();
        }

        return supplierRepository.findByOrganizationCodeIgnoreCase(organizationCode.trim())
                .stream()
                .map(s -> new LookupDto(s.getSupplierCode(), s.getSupplierName()))
                .toList();
    }
    @GetMapping("/supplier-name")
    public LookupDto supplierName(@RequestParam("supplierCode") String supplierCode) {
        return supplierRepository.findById(supplierCode)
                .map(s -> new LookupDto(s.getSupplierCode(), s.getSupplierName()))
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }



}
