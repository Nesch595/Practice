CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    hotelId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    bookingId BIGINT,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    isDeleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_review_hotel FOREIGN KEY (hotelId) REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_user FOREIGN KEY (userId) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_review_booking FOREIGN KEY (bookingId) REFERENCES bookings(id) ON DELETE SET NULL,
    CONSTRAINT uq_review_booking UNIQUE (bookingId)
);