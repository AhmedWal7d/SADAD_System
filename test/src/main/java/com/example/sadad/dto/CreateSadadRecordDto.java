package com.example.sadad.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record CreateSadadRecordDto(
        @NotBlank String organizationCode,
        @NotBlank String legalEntity,
        @NotBlank String remitterBank,
        @NotBlank String remitterBankAccount,
        @NotBlank String billerId,
        @NotBlank String vendorNumber,
        String vendorSiteCode,
        @NotBlank String invoiceType,
        @NotBlank String invoiceNumber,
        @NotBlank String subscriptionAccountNumber,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank String expenseAccountCode,
        @NotBlank String businessCode,
        @NotBlank String locationCode,
        @NotNull @Size(min = 1) @Valid List<SadadCostCenterDto> costCenters
) {}
