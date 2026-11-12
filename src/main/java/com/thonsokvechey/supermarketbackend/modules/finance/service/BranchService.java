package com.thonsokvechey.supermarketbackend.modules.finance.service;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BranchDto;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    List<BranchDto> getAllBranches();

    BranchDto createBranch(BranchDto request);

    BranchDto updateBranch(UUID id, BranchDto request);
}

