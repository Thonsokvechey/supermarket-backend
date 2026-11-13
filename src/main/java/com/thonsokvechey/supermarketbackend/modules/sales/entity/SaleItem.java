package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.inventory.entity.InventoryBatch;
import com.thonsokvechey.supermarketbackend.modules.inventory.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private InventoryBatch batch;

    @Column(name = "quantity", precision = 18, scale = 3, nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 18, scale = 4, nullable = false)
    private BigDecimal unitPrice;

    @Builder.Default
    @Column(name = "discount_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "line_total", precision = 18, scale = 4, nullable = false)
    private BigDecimal lineTotal;

    @Builder.Default
    @Column(name = "unit_cost_base_snapshot", precision = 18, scale = 4, nullable = false)
    private BigDecimal unitCostBaseSnapshot = BigDecimal.ZERO;
}

