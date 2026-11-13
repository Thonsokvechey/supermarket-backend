package com.thonsokvechey.supermarketbackend.modules.sales.entity;

import com.thonsokvechey.supermarketbackend.modules.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cash_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CashSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id", nullable = false)
    private CashRegister register;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Builder.Default
    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt = OffsetDateTime.now();

    @Column(name = "closed_at")
    private OffsetDateTime closedAt;

    @Builder.Default
    @Column(name = "opening_usd", precision = 18, scale = 2, nullable = false)
    private BigDecimal openingUsd = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "opening_khr", precision = 18, scale = 0, nullable = false)
    private BigDecimal openingKhr = BigDecimal.ZERO;

    @Column(name = "counted_closing_usd", precision = 18, scale = 2)
    private BigDecimal countedClosingUsd;

    @Column(name = "counted_closing_khr", precision = 18, scale = 0)
    private BigDecimal countedClosingKhr;

    @Column(name = "expected_closing_usd", precision = 18, scale = 2)
    private BigDecimal expectedClosingUsd;

    @Column(name = "expected_closing_khr", precision = 18, scale = 0)
    private BigDecimal expectedClosingKhr;

    @Builder.Default
    @Column(name = "status", length = 20, nullable = false)
    private String status = "OPEN";
}

