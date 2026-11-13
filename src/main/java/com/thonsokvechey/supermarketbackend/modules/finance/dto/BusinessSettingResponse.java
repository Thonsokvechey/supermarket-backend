package com.thonsokvechey.supermarketbackend.modules.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSettingResponse {

    private UUID id;
    private UUID branchId;
    private String branchCode;
    private String branchName;
    private String baseCurrencyCode;
    private String baseCurrencyName;
    private String baseCurrencySymbol;
    private String timezone;
    private boolean allowNegativeStock;
    private OffsetDateTime createdAt;
}

