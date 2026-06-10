CREATE TABLE IF NOT EXISTS rooms (
    id BIGSERIAL PRIMARY KEY,
    hotelId BIGINT NOT NULL REFERENCES hotels(id) ON DELETE RESTRICT,
    roomType VARCHAR(10) NOT NULL CHECK(roomType IN ('SINGLE', 'DOUBLE', 'SUITE', 'DELUXE')),
    pricePerNight DECIMAL(10,2) NOT NULL CHECK (pricePerNight > 0),
    capacity INTEGER NOT NULL DEFAULT 1 CHECK (capacity > 0),
    description TEXT,
    amenities TEXT[],
    area DECIMAL(6,1) NOT NULL CHECK (area > 0),
    isDeleted BOOLEAN NOT NULL DEFAULT FALSE
);