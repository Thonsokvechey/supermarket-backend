package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "sale_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SalePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(name = "payment_method", length = 30, nullable = false)
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Column(name = "tender_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal tenderAmount;

    @Builder.Default
    @Column(name = "fx_rate_to_sale", precision = 18, scale = 8, nullable = false)
    private BigDecimal fxRateToSale = BigDecimal.ONE;

    @Column(name = "applied_amount_sale_currency", precision = 18, scale = 4, nullable = false)
    private BigDecimal appliedAmountSaleCurrency;

    @Builder.Default
    @Column(name = "change_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal changeAmount = BigDecimal.ZERO;

    @Column(name = "reference_number", length = 120)
    private String referenceNumber;

    @Builder.Default
    @Column(name = "paid_at", nullable = false)
    private OffsetDateTime paidAt = OffsetDateTime.now();
}

