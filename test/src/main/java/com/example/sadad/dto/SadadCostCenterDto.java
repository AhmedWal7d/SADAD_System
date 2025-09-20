package com.example.sadad.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SadadCostCenterDto(
        @NotBlank String costCenterCode,
        String costCenterDesc,
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal percentage
) {}

