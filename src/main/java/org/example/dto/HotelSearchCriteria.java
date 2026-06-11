package org.example.dto;

public record HotelSearchCriteria(
        String country,
        String city,
        String name,
        Double minRating,
        Double maxRating,
        String amenities
) { }
