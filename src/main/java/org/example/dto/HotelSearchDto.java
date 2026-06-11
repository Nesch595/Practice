package org.example.dto;

public record HotelSearchDto(
        String name,
        String city,
        String country,
        String address,
        Double rating
) { }
