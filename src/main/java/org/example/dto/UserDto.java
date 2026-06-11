package org.example.dto;

import org.example.entity.Role;

import java.time.LocalDateTime;

public record UserDto (
        String username,
        String email,
        Role role,
        boolean isDeleted,
        LocalDateTime createdAt
){ }
