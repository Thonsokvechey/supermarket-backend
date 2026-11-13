package com.thonsokvechey.supermarketbackend.modules.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "journal_entry_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JournalEntryLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_id", nullable = false)
    private JournalEntry journalEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Builder.Default
    @Column(name = "foreign_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal foreignAmount = BigDecimal.ZERO;

    @Column(name = "exchange_rate_to_base", precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRateToBase;

    @Builder.Default
    @Column(name = "debit_base", precision = 18, scale = 4, nullable = false)
    private BigDecimal debitBase = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "credit_base", precision = 18, scale = 4, nullable = false)
    private BigDecimal creditBase = BigDecimal.ZERO;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}

