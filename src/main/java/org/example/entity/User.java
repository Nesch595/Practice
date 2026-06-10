package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passwordHash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}
