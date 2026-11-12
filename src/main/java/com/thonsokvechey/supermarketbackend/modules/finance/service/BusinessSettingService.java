package com.thonsokvechey.supermarketbackend.modules.finance.service;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BusinessSettingResponse;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.UpdateBusinessSettingRequest;

import java.util.List;
import java.util.UUID;

public interface BusinessSettingService {

    List<BusinessSettingResponse> getAllSettings();

    BusinessSettingResponse getSettingsByBranchId(UUID branchId);

    BusinessSettingResponse updateSettingsByBranchId(UUID branchId, UpdateBusinessSettingRequest request);
}

