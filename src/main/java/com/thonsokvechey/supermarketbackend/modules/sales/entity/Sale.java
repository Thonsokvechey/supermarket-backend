package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.employee.entity.Employee;
import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "sales",
        indexes = {
                @Index(name = "idx_sales_date", columnList = "sale_date DESC"),
                @Index(name = "idx_sales_cashier", columnList = "cashier_employee_id, sale_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "receipt_number", length = 50, unique = true, nullable = false)
    private String receiptNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_session_id", nullable = false)
    private CashSession cashSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_employee_id", nullable = false)
    private Employee cashierEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Column(name = "exchange_rate_to_base", precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRateToBase;

    @Builder.Default
    @Column(name = "sale_date", nullable = false)
    private OffsetDateTime saleDate = OffsetDateTime.now();

    @Column(name = "subtotal", precision = 18, scale = 4, nullable = false)
    private BigDecimal subtotal;

    @Builder.Default
    @Column(name = "discount_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "tax_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "total_base", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalBase;

    @Builder.Default
    @Column(name = "status", length = 20, nullable = false)
    private String status = "COMPLETED";

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

