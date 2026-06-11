package org.example.dto;

public record HotelDto(
        Long id,
        String name,
        String city,
        String country,
        String address,
        Double rating
) { }
