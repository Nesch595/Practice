package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId")
    private Booking booking;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}