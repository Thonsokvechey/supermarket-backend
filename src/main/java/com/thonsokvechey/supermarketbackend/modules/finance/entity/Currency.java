package com.thonsokvechey.supermarketbackend.modules.finance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Currency {

    @Id
    @Column(name = "code", length = 3, nullable = false)
    @EqualsAndHashCode.Include
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "symbol", length = 8, nullable = false)
    private String symbol;

    @Column(name = "decimal_places", nullable = false)
    private Short decimalPlaces;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}

