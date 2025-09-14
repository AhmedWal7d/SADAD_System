package com.example.sadad.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateSadadRecordDto(
        @NotBlank String organizationCode,
        String organizationName,
        @NotBlank String legalEntity,
        @NotBlank String remitterBank,
        String remitterBankAccount,
        String remitterBankAccountName,
        @NotBlank String billerId,
        String billerName,
        @NotBlank String vendorNumber,
        String vendorName,
        String vendorSiteCode,
        @NotBlank String invoiceType,
        @NotBlank String invoiceNumber,
        @NotBlank String subscriptionAccountNumber,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank String expenseAccountCode,
        String expenseAccountName,
        @NotBlank String businessCode,
        String businessName,
        @NotBlank String locationCode,
        String locationName
) {}
