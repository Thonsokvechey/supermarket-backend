package com.thonsokvechey.supermarketbackend.modules.finance.repository;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    @Query("""
            SELECT er FROM ExchangeRate er
            JOIN FETCH er.fromCurrency
            JOIN FETCH er.toCurrency
            ORDER BY er.validFrom DESC
            """)
    List<ExchangeRate> findAllWithCurrencies();
}

