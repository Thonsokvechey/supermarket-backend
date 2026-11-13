package com.thonsokvechey.supermarketbackend.modules.inventory.entity;

import com.thonsokvechey.supermarketbackend.modules.finance.entity.Branch;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "stock_locations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "location_code"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "location_code", length = 30, nullable = false)
    private String locationCode;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "location_type", length = 30, nullable = false)
    private String locationType;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}

