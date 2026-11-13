package com.thonsokvechey.supermarketbackend.modules.finance.service;

import com.thonsokvechey.supermarketbackend.modules.finance.dto.CurrencyDto;
import com.thonsokvechey.supermarketbackend.modules.finance.dto.ExchangeRateDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getAllCurrencies();

    CurrencyDto saveCurrency(CurrencyDto request);

    List<ExchangeRateDto> getAllExchangeRates();

    ExchangeRateDto createExchangeRate(ExchangeRateDto request);
}

