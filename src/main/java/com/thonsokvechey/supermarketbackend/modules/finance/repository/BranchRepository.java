package com.thonsokvechey.supermarketbackend.modules.finance.repository;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCaseAndIdNot(String code, UUID id);

    Optional<Branch> findByCodeIgnoreCase(String code);
}

