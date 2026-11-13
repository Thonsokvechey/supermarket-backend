package com.thonsokvechey.supermarketbackend.modules.finance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "account_code", length = 30, unique = true, nullable = false)
    private String accountCode;

    @Column(name = "account_name", length = 150, nullable = false)
    private String accountName;

    @Column(name = "account_type", length = 20, nullable = false)
    private String accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_account_id")
    private Account parentAccount;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}

