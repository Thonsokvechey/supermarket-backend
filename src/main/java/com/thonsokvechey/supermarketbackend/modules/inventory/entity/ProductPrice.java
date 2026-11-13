package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Currency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "product_prices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "currency_code", "valid_from"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    @Column(name = "selling_price", precision = 18, scale = 4, nullable = false)
    private BigDecimal sellingPrice;

    @Column(name = "valid_from", nullable = false)
    private OffsetDateTime validFrom;

    @Column(name = "valid_to")
    private OffsetDateTime validTo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

