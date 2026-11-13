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
public class BranchDto {

    private UUID id;

    @NotBlank(message = "Branch code is required")
    @Size(max = 30, message = "Branch code must not exceed 30 characters")
    private String code;

    @NotBlank(message = "Branch name is required")
    @Size(max = 150, message = "Branch name must not exceed 150 characters")
    private String name;

    private String address;

    @Size(max = 30, message = "Phone must not exceed 30 characters")
    private String phone;

    private Boolean isActive;

    private OffsetDateTime createdAt;
}

