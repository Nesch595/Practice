package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(name = "checkInDate", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "checkOutDate", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "totalPrice", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY)
    private Review review;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}