package com.thonsokvechey.supermarketbackend.modules.finance.service.impl;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.CurrencyDto;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.ExchangeRateDto;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.ExchangeRate;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.CurrencyRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.ExchangeRateRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(this::toCurrencyDto)
                .toList();
    }

    @Override
    @Transactional
    public CurrencyDto saveCurrency(CurrencyDto request) {
        String code = request.getCode().trim().toUpperCase();
        if (request.getDecimalPlaces() < 0 || request.getDecimalPlaces() > 4) {
            throw new IllegalArgumentException("decimalPlaces must be between 0 and 4");
        }

        Currency currency = Currency.builder()
                .code(code)
                .name(request.getName().trim())
                .symbol(request.getSymbol().trim())
                .decimalPlaces(request.getDecimalPlaces())
                .isActive(request.getIsActive() == null || request.getIsActive())
                .build();

        Currency saved = currencyRepository.save(currency);
        return toCurrencyDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExchangeRateDto> getAllExchangeRates() {
        return exchangeRateRepository.findAllWithCurrencies().stream()
                .map(this::toExchangeRateDto)
                .toList();
    }

    @Override
    @Transactional
    public ExchangeRateDto createExchangeRate(ExchangeRateDto request) {
        String fromCode = request.getFromCurrency().trim().toUpperCase();
        String toCode = request.getToCurrency().trim().toUpperCase();

        if (fromCode.equals(toCode)) {
            throw new IllegalArgumentException("fromCurrency and toCurrency must be different");
        }

        if (request.getRate() == null || request.getRate().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Exchange rate must be greater than 0");
        }

        Currency fromCurrency = currencyRepository.findById(fromCode)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + fromCode));
        Currency toCurrency = currencyRepository.findById(toCode)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + toCode));

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .rate(request.getRate())
                .validFrom(request.getValidFrom())
                .source(request.getSource())
                .approvedBy(request.getApprovedBy())
                .build();

        ExchangeRate saved = exchangeRateRepository.save(exchangeRate);
        return toExchangeRateDto(saved);
    }

    private CurrencyDto toCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .code(currency.getCode())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .decimalPlaces(currency.getDecimalPlaces())
                .isActive(currency.isActive())
                .build();
    }

    private ExchangeRateDto toExchangeRateDto(ExchangeRate er) {
        return ExchangeRateDto.builder()
                .id(er.getId())
                .fromCurrency(er.getFromCurrency().getCode())
                .toCurrency(er.getToCurrency().getCode())
                .rate(er.getRate())
                .validFrom(er.getValidFrom())
                .source(er.getSource())
                .approvedBy(er.getApprovedBy())
                .createdAt(er.getCreatedAt())
                .build();
    }
}

