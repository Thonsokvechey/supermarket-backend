package com.thonsokvechey.supermarketbackend.modules.supplier.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "supplier_payment_allocations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierPaymentAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_payment_id", nullable = false)
    private SupplierPayment supplierPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_invoice_id", nullable = false)
    private SupplierInvoice supplierInvoice;

    @Column(name = "allocated_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal allocatedAmount;
}

