package com.thonsokvechey.supermarketbackend.modules.purchasing.entity;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "purchase_orders",
        indexes = @Index(name = "idx_po_supplier", columnList = "supplier_id, order_date DESC")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "order_number", length = 40, unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Builder.Default
    @Column(name = "exchange_rate_to_base", precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRateToBase = BigDecimal.ONE;

    @Builder.Default
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate = LocalDate.now();

    @Column(name = "expected_date")
    private LocalDate expectedDate;

    @Builder.Default
    @Column(name = "status", length = 20, nullable = false)
    private String status = "DRAFT";

    @Builder.Default
    @Column(name = "subtotal", precision = 18, scale = 4, nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "discount_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "total_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

