package org.example.mapper;

import org.example.dto.HotelDto;
import org.example.entity.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDto toDto(Hotel hotel);
    Hotel toEntity(HotelDto hotelDto);
}