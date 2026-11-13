package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "product_barcodes",
        indexes = @Index(name = "idx_barcodes_barcode", columnList = "barcode")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "barcode", length = 80, unique = true, nullable = false)
    private String barcode;

    @Builder.Default
    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary = false;
}

