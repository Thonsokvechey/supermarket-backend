package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "inventory_batches",
        indexes = @Index(name = "idx_inventory_expiry", columnList = "expiry_date")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InventoryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "batch_number", length = 80, nullable = false)
    private String batchNumber;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Builder.Default
    @Column(name = "unit_cost_base", precision = 18, scale = 4, nullable = false)
    private BigDecimal unitCostBase = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

