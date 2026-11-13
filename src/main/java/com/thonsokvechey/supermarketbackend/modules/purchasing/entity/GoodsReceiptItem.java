package com.thonsokvechey.supermarketbackend.modules.purchasing.entity;

import com.thonsokvechey.supermarketbackend.modules.inventory.entity.InventoryBatch;
import com.thonsokvechey.supermarketbackend.modules.inventory.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "goods_receipt_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GoodsReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_receipt_id", nullable = false)
    private GoodsReceipt goodsReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_item_id")
    private PurchaseOrderItem purchaseOrderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InventoryBatch batch;

    @Builder.Default
    @Column(name = "accepted_quantity", precision = 18, scale = 3, nullable = false)
    private BigDecimal acceptedQuantity = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "rejected_quantity", precision = 18, scale = 3, nullable = false)
    private BigDecimal rejectedQuantity = BigDecimal.ZERO;
}

