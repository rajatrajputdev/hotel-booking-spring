package net.rajatrajputdev.hotelbooking.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookingRoomRequest(
        @NotBlank(message = "Booking ID is required")
        String bookingId,

        @NotBlank(message = "Room ID is required")
        String roomId
) {}
