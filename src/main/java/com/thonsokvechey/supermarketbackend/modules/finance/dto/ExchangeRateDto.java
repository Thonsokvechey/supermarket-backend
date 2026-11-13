package com.thonsokvechey.supermarketbackend.modules.finance.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private UUID id;

    @NotBlank(message = "fromCurrency is required")
    @Size(min = 3, max = 3, message = "fromCurrency must be a 3-letter currency code")
    private String fromCurrency;

    @NotBlank(message = "toCurrency is required")
    @Size(min = 3, max = 3, message = "toCurrency must be a 3-letter currency code")
    private String toCurrency;

    @NotNull(message = "Exchange rate is required")
    @DecimalMin(value = "0.00000001", message = "Exchange rate must be greater than 0")
    private BigDecimal rate;

    @NotNull(message = "validFrom timestamp is required")
    private OffsetDateTime validFrom;

    @Size(max = 100, message = "Source must not exceed 100 characters")
    private String source;

    private UUID approvedBy;

    private OffsetDateTime createdAt;
}

