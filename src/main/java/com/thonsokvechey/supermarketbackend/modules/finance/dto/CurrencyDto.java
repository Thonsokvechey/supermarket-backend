package com.thonsokvechey.supermarketbackend.modules.finance.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters (ISO 4217)")
    private String code;

    @NotBlank(message = "Currency name is required")
    @Size(max = 50, message = "Currency name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Currency symbol is required")
    @Size(max = 8, message = "Currency symbol must not exceed 8 characters")
    private String symbol;

    @NotNull(message = "Decimal places is required")
    @Min(value = 0, message = "Decimal places must be between 0 and 4")
    @Max(value = 4, message = "Decimal places must be between 0 and 4")
    private Short decimalPlaces;

    private Boolean isActive;
}

