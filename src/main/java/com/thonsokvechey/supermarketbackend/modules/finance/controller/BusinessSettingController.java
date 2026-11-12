package com.thonsokvechey.supermarketbackend.modules.finance.controller;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BusinessSettingResponse;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.UpdateBusinessSettingRequest;
import com.thonsokvechey.supermarketbackend.modules.finance.service.BusinessSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business-settings")
@RequiredArgsConstructor
public class BusinessSettingController {

    private final BusinessSettingService businessSettingService;

    @GetMapping
    public ResponseEntity<List<BusinessSettingResponse>> getAllSettings() {
        return ResponseEntity.ok(businessSettingService.getAllSettings());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<BusinessSettingResponse> getSettingsByBranchId(@PathVariable UUID branchId) {
        return ResponseEntity.ok(businessSettingService.getSettingsByBranchId(branchId));
    }

    @PutMapping("/branch/{branchId}")
    public ResponseEntity<BusinessSettingResponse> updateSettingsByBranchId(
            @PathVariable UUID branchId,
            @Valid @RequestBody UpdateBusinessSettingRequest request
    ) {
        return ResponseEntity.ok(businessSettingService.updateSettingsByBranchId(branchId, request));
    }
}

