package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.inventory.entity.InventoryBatch;
import com.thonsokvechey.supermarketbackend.modules.inventory.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sale_refund_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleRefundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_refund_id", nullable = false)
    private SaleRefund saleRefund;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private InventoryBatch batch;

    @Column(name = "quantity", precision = 18, scale = 3, nullable = false)
    private BigDecimal quantity;

    @Column(name = "refund_line_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal refundLineAmount;

    @Builder.Default
    @Column(name = "restock", nullable = false)
    private boolean restock = true;
}

