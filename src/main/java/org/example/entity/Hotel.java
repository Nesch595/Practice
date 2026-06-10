package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerId", nullable = false)
    private User manager;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
}