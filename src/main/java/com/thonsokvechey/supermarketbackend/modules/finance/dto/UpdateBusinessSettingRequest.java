package com.thonsokvechey.supermarketbackend.modules.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessSettingRequest {

    @NotBlank(message = "Base currency code is required (e.g., KHR or USD)")
    @Size(min = 3, max = 3, message = "Base currency code must be 3 characters")
    private String baseCurrencyCode;

    @Size(max = 60, message = "Timezone must not exceed 60 characters")
    private String timezone;

    private Boolean allowNegativeStock;
}

