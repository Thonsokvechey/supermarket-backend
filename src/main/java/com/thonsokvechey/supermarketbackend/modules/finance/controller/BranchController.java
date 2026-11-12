package com.thonsokvechey.supermarketbackend.modules.finance.controller;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.BranchDto;
import com.thonsokvechey.supermarketbackend.modules.finance.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.createBranch(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(
            @PathVariable UUID id,
            @Valid @RequestBody BranchDto request
    ) {
        return ResponseEntity.ok(branchService.updateBranch(id, request));
    }
}

