package com.example.sadad.dto;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CostCenterDto(
        @NotBlank String costCenterCode,
        String costCenterDesc,
        @NotNull @DecimalMin("0.01") @DecimalMax("100.00") BigDecimal percentage
) {}

