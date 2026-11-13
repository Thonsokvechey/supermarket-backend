package com.thonsokvechey.supermarketbackend.modules.finance.service.impl;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BusinessSettingResponse;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.UpdateBusinessSettingRequest;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.BusinessSetting;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.BusinessSettingRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.CurrencyRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.service.BusinessSettingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessSettingServiceImpl implements BusinessSettingService {

    private final BusinessSettingRepository businessSettingRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BusinessSettingResponse> getAllSettings() {
        return businessSettingRepository.findAllWithBranchAndCurrency().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessSettingResponse getSettingsByBranchId(UUID branchId) {
        BusinessSetting setting = businessSettingRepository.findByBranchIdWithDetails(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Business settings not found for branch id: " + branchId));
        return toResponse(setting);
    }

    @Override
    @Transactional
    public BusinessSettingResponse updateSettingsByBranchId(UUID branchId, UpdateBusinessSettingRequest request) {
        BusinessSetting setting = businessSettingRepository.findByBranchIdWithDetails(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Business settings not found for branch id: " + branchId));

        if (request.getBaseCurrencyCode() != null && !request.getBaseCurrencyCode().isBlank()) {
            String currencyCode = request.getBaseCurrencyCode().trim().toUpperCase();
            Currency currency = currencyRepository.findById(currencyCode)
                    .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + currencyCode));
            if (!currency.isActive()) {
                throw new IllegalArgumentException("Currency '" + currencyCode + "' is not active");
            }
            setting.setBaseCurrency(currency);
        }

        if (request.getTimezone() != null && !request.getTimezone().isBlank()) {
            setting.setTimezone(request.getTimezone().trim());
        }

        if (request.getAllowNegativeStock() != null) {
            setting.setAllowNegativeStock(request.getAllowNegativeStock());
        }

        BusinessSetting saved = businessSettingRepository.save(setting);
        return toResponse(saved);
    }

    private BusinessSettingResponse toResponse(BusinessSetting setting) {
        return BusinessSettingResponse.builder()
                .id(setting.getId())
                .branchId(setting.getBranch().getId())
                .branchCode(setting.getBranch().getCode())
                .branchName(setting.getBranch().getName())
                .baseCurrencyCode(setting.getBaseCurrency().getCode())
                .baseCurrencyName(setting.getBaseCurrency().getName())
                .baseCurrencySymbol(setting.getBaseCurrency().getSymbol())
                .timezone(setting.getTimezone())
                .allowNegativeStock(setting.isAllowNegativeStock())
                .createdAt(setting.getCreatedAt())
                .build();
    }
}

