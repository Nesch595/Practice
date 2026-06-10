package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User user;

    @Column(name = "totalPoints", nullable = false)
    private Integer totalPoints;

    @Column(name = "tier", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoyaltyTier tier;

    @Column(name = "totalSpent", nullable = false)
    private BigDecimal totalSpent;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}