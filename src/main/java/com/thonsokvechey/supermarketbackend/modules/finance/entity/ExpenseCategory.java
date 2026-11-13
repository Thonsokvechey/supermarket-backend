package com.thonsokvechey.supermarketbackend.modules.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "expense_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}

