package org.example.service;

import org.example.dto.HotelDto;
import org.example.dto.HotelSearchCriteria;
import org.example.dto.HotelSearchDto;
import org.example.entity.Hotel;
import org.example.entity.UserContext;
import org.example.mapper.HotelMapper;
import org.example.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final UserContext userContext;

    @Autowired
    public HotelService(HotelMapper hotelMapper, HotelRepository hotelRepository, UserContext userContext) {
        this.hotelMapper = hotelMapper;
        this.hotelRepository = hotelRepository;
        this.userContext = userContext;
    }

    public void createHotel(HotelDto hotelDto) {
        if (!userContext.isAdmin() && !userContext.isHotelManager()) {
            throw new SecurityException("Only admin or hotel manager can create hotels");
        }

        boolean exists = hotelRepository.findAll().stream()
                .anyMatch(hotel -> hotel.getName().equalsIgnoreCase(hotelDto.name()) &&
                        hotel.getCity().equalsIgnoreCase(hotelDto.city()) &&
                        !hotel.isDeleted());

        if (exists) {
            throw new RuntimeException(
                    String.format("Hotel with name '%s' already exists in city '%s'",
                            hotelDto.name(), hotelDto.city())
            );
        }

        Hotel hotel = hotelMapper.toEntity(hotelDto);
        hotel.setDeleted(false);

        if (userContext.isHotelManager()) {
            hotel.setManager(userContext.getCurrentUser());
        }

        hotelRepository.save(hotel);
        System.out.println("Hotel '" + hotel.getName() + "' created successfully!");
    }

    public void updateHotel(Long hotelId, HotelDto hotelDto) {
        if (!userContext.isAdmin() && !userContext.isHotelManager()) {
            throw new SecurityException("Only admin or hotel manager can update hotels");
        }

        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        if (userContext.isHotelManager() &&
                !existingHotel.getManager().getId().equals(userContext.getCurrentUser().getId())) {
            throw new SecurityException("You can only edit your own hotels");
        }

        if (existingHotel.isDeleted()) {
            throw new RuntimeException("Cannot edit deleted hotel");
        }

        if (!existingHotel.getName().equalsIgnoreCase(hotelDto.name()) ||
                !existingHotel.getCity().equalsIgnoreCase(hotelDto.city())) {

            boolean nameExistsInCity = hotelRepository.findAll().stream()
                    .anyMatch(hotel -> !hotel.getId().equals(hotelId) &&
                            hotel.getName().equalsIgnoreCase(hotelDto.name()) &&
                            hotel.getCity().equalsIgnoreCase(hotelDto.city()) &&
                            !hotel.isDeleted());

            if (nameExistsInCity) {
                throw new RuntimeException(
                        String.format("Hotel with name '%s' already exists in city '%s'",
                                hotelDto.name(), hotelDto.city())
                );
            }
        }

        existingHotel.setName(hotelDto.name());
        existingHotel.setCity(hotelDto.city());
        existingHotel.setCountry(hotelDto.country());
        existingHotel.setAddress(hotelDto.address());
        existingHotel.setRating(hotelDto.rating());

        hotelRepository.save(existingHotel);
        System.out.println("Hotel '" + existingHotel.getName() + "' updated successfully!");
    }

    public Page<HotelDto> getAllHotelsForAdmin(Pageable pageable) {
        return hotelRepository.findAll(pageable).map(hotelMapper::toDto);
    }

    public Page<HotelSearchDto> searchHotels(HotelSearchCriteria criteria, Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.searchHotels(
                criteria.country(),
                criteria.city(),
                criteria.name(),
                criteria.minRating(),
                criteria.maxRating(),
                pageable
        );

        return hotels.map(hotel -> new HotelSearchDto(
                hotel.getName(),
                hotel.getCity(),
                hotel.getCountry(),
                hotel.getAddress(),
                hotel.getRating()
        ));
    }

    public HotelDto getHotelDetails(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotelMapper.toDto(hotel);
    }
}
