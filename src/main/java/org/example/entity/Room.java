package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotel;

    @Column(name = "roomType", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "pricePerNight", nullable = false)
    private BigDecimal pricePerNight;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "description")
    private String description;

    @Column(name = "amenities")
    private List<String> amenities;

    @Column(name = "area", nullable = false)
    private BigDecimal area;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}