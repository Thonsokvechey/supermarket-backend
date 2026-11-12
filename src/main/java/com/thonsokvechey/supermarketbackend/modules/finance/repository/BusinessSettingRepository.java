package com.thonsokvechey.supermarketbackend.modules.finance.repository;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.BusinessSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessSettingRepository extends JpaRepository<BusinessSetting, UUID> {

    @Query("""
            SELECT bs FROM BusinessSetting bs
            JOIN FETCH bs.branch
            JOIN FETCH bs.baseCurrency
            """)
    List<BusinessSetting> findAllWithBranchAndCurrency();

    @Query("""
            SELECT bs FROM BusinessSetting bs
            JOIN FETCH bs.branch b
            JOIN FETCH bs.baseCurrency
            WHERE b.id = :branchId
            """)
    Optional<BusinessSetting> findByBranchIdWithDetails(@Param("branchId") UUID branchId);

    boolean existsByBranchId(UUID branchId);
}

