package com.thonsokvechey.supermarketbackend.modules.finance.service.impl;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BranchDto;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Branch;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.BusinessSetting;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.BranchRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.BusinessSettingRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.CurrencyRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BusinessSettingRepository businessSettingRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::toBranchDto)
                .toList();
    }

    @Override
    @Transactional
    public BranchDto createBranch(BranchDto request) {
        String code = request.getCode().trim().toUpperCase();
        if (branchRepository.existsByCodeIgnoreCase(code)) {
            throw new IllegalArgumentException("Branch code '" + code + "' already exists");
        }

        Branch branch = Branch.builder()
                .code(code)
                .name(request.getName().trim())
                .address(request.getAddress())
                .phone(request.getPhone())
                .isActive(request.getIsActive() == null || request.getIsActive())
                .build();

        Branch savedBranch = branchRepository.save(branch);

        // Create default business_settings record with KHR base currency for the new branch
        Currency defaultBaseCurrency = currencyRepository.findById("KHR")
                .orElseThrow(() -> new EntityNotFoundException("Default base currency KHR not found"));

        businessSettingRepository.save(BusinessSetting.builder()
                .branch(savedBranch)
                .baseCurrency(defaultBaseCurrency)
                .timezone("Asia/Phnom_Penh")
                .allowNegativeStock(false)
                .build());

        return toBranchDto(savedBranch);
    }

    @Override
    @Transactional
    public BranchDto updateBranch(UUID id, BranchDto request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));

        if (request.getCode() != null && !request.getCode().isBlank()) {
            String code = request.getCode().trim().toUpperCase();
            if (branchRepository.existsByCodeIgnoreCaseAndIdNot(code, id)) {
                throw new IllegalArgumentException("Branch code '" + code + "' already exists");
            }
            branch.setCode(code);
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            branch.setName(request.getName().trim());
        }

        if (request.getAddress() != null) {
            branch.setAddress(request.getAddress());
        }

        if (request.getPhone() != null) {
            branch.setPhone(request.getPhone());
        }

        if (request.getIsActive() != null) {
            branch.setActive(request.getIsActive());
        }

        Branch saved = branchRepository.save(branch);
        return toBranchDto(saved);
    }

    private BranchDto toBranchDto(Branch branch) {
        return BranchDto.builder()
                .id(branch.getId())
                .code(branch.getCode())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .isActive(branch.isActive())
                .createdAt(branch.getCreatedAt())
                .build();
    }
}

