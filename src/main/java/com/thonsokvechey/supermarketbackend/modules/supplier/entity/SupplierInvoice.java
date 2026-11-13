package com.thonsokvechey.supermarketbackend.modules.supplier.entity;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import com.thonsokvechey.supermarketbackend.modules.purchasing.entity.PurchaseOrder;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "supplier_invoices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"supplier_id", "invoice_number"}),
        indexes = @Index(name = "idx_supplier_invoice_due", columnList = "due_date")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "invoice_number", length = 80, nullable = false)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Column(name = "exchange_rate_to_base", precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRateToBase;

    @Column(name = "total_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalAmount;

    @Builder.Default
    @Column(name = "status", length = 20, nullable = false)
    private String status = "UNPAID";
}

