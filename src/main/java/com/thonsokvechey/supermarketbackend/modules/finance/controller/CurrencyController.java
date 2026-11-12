package com.thonsokvechey.supermarketbackend.modules.finance.controller;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.CurrencyDto;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.ExchangeRateDto;
import com.thonsokvechey.supermarketbackend.modules.finance.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @PostMapping("/currencies")
    public ResponseEntity<CurrencyDto> saveCurrency(@Valid @RequestBody CurrencyDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.saveCurrency(request));
    }

    @GetMapping("/exchange-rates")
    public ResponseEntity<List<ExchangeRateDto>> getAllExchangeRates() {
        return ResponseEntity.ok(currencyService.getAllExchangeRates());
    }

    @PostMapping("/exchange-rates")
    public ResponseEntity<ExchangeRateDto> createExchangeRate(@Valid @RequestBody ExchangeRateDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.createExchangeRate(request));
    }
}

