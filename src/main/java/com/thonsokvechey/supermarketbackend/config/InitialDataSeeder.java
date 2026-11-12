package com.thonsokvechey.supermarketbackend.config;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Branch;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.BusinessSetting;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.BranchRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.BusinessSettingRepository;
import com.thonsokvechey.supermarketbackend.modules.finance.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataSeeder implements CommandLineRunner {

    private final CurrencyRepository currencyRepository;
    private final BranchRepository branchRepository;
    private final BusinessSettingRepository businessSettingRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedCurrencies();
        seedInitialBranchAndSettings();
    }

    private void seedCurrencies() {
        if (!currencyRepository.existsById("USD")) {
            currencyRepository.save(Currency.builder()
                    .code("USD")
                    .name("US Dollar")
                    .symbol("$")
                    .decimalPlaces((short) 2)
                    .isActive(true)
                    .build());
            log.info("Seeded default currency: USD");
        }

        if (!currencyRepository.existsById("KHR")) {
            currencyRepository.save(Currency.builder()
                    .code("KHR")
                    .name("Cambodian Riel")
                    .symbol("៛")
                    .decimalPlaces((short) 0)
                    .isActive(true)
                    .build());
            log.info("Seeded default currency: KHR");
        }
    }

    private void seedInitialBranchAndSettings() {
        Currency defaultBaseCurrency = currencyRepository.findById("KHR")
                .orElseThrow(() -> new IllegalStateException("Default currency KHR must exist"));

        if (branchRepository.count() == 0) {
            Branch initialBranch = branchRepository.save(Branch.builder()
                    .code("MAIN")
                    .name("Main Branch")
                    .address("Phnom Penh, Cambodia")
                    .phone("")
                    .isActive(true)
                    .build());

            businessSettingRepository.save(BusinessSetting.builder()
                    .branch(initialBranch)
                    .baseCurrency(defaultBaseCurrency)
                    .timezone("Asia/Phnom_Penh")
                    .allowNegativeStock(false)
                    .build());

            log.info("Initial installation: created default branch '{}' and business settings with base currency KHR",
                    initialBranch.getCode());
        } else if (businessSettingRepository.count() == 0) {
            Branch firstBranch = branchRepository.findAll().getFirst();
            businessSettingRepository.save(BusinessSetting.builder()
                    .branch(firstBranch)
                    .baseCurrency(defaultBaseCurrency)
                    .timezone("Asia/Phnom_Penh")
                    .allowNegativeStock(false)
                    .build());

            log.info("Created default business settings (base currency KHR) for branch '{}'", firstBranch.getCode());
        }
    }
}

