package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "stock_movements",
        indexes = @Index(name = "idx_stock_movement_product_date", columnList = "product_id, created_at DESC")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private StockLocation location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private InventoryBatch batch;

    @Column(name = "movement_type", length = 30, nullable = false)
    private String movementType;

    @Column(name = "quantity_delta", precision = 18, scale = 3, nullable = false)
    private BigDecimal quantityDelta;

    @Builder.Default
    @Column(name = "unit_cost_base", precision = 18, scale = 4, nullable = false)
    private BigDecimal unitCostBase = BigDecimal.ZERO;

    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

