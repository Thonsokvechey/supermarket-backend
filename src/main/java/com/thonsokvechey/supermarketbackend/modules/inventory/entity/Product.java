package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_products_name", columnList = "name"),
                @Index(name = "idx_products_category", columnList = "category_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "sku", length = 50, unique = true, nullable = false)
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(name = "name", length = 180, nullable = false)
    private String name;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "track_expiry", nullable = false)
    private boolean trackExpiry = false;

    @Builder.Default
    @Column(name = "track_batch", nullable = false)
    private boolean trackBatch = false;

    @Builder.Default
    @Column(name = "min_stock", precision = 18, scale = 3, nullable = false)
    private BigDecimal minStock = BigDecimal.ZERO;

    @Column(name = "max_stock", precision = 18, scale = 3)
    private BigDecimal maxStock;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}

