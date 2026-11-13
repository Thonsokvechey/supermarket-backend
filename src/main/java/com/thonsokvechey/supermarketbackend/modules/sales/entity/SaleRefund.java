package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "sale_refunds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleRefund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "refund_number", length = 50, unique = true, nullable = false)
    private String refundNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Builder.Default
    @Column(name = "refund_date", nullable = false)
    private OffsetDateTime refundDate = OffsetDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Column(name = "refund_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal refundAmount;

    @Column(name = "reason", columnDefinition = "TEXT", nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}

