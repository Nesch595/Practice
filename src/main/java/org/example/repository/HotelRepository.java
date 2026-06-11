package org.example.repository;

import org.example.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByAddress(String address);
    Page<Hotel> findByRatingBetween(Double minRating, Double maxRating, Pageable pageable);
    Page<Hotel> findByRatingGreaterThanEqual(Double minRating, Pageable pageable);
    Page<Hotel> findByRatingLessThanEqual(Double maxRating, Pageable pageable);

    @Query("SELECT h FROM Hotel h WHERE " +
            "(:city IS NULL OR LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:minRating IS NULL OR h.rating >= :minRating) AND " +
            "(:maxRating IS NULL OR h.rating <= :maxRating))")
    Page<Hotel> searchHotels(@Param("country") String country, @Param("city") String city,
                             @Param("name") String name, @Param("minRating") Double minRating,
                             @Param("maxRating") Double maxRating, Pageable pageable);
}
