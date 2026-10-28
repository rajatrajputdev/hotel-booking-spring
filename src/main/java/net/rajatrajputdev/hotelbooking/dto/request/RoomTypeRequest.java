package net.rajatrajputdev.hotelbooking.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomTypeRequest(

        @NotBlank(message = "Room type name is required")
        String name,

        @NotBlank(message = "Room type description is required")
        String description,

        @NotNull(message = "Price per night is required")
        @Min(value = 1, message = "Price per night must be greater than zero")
        Integer pricePerNight,

        @NotNull(message = "Room capacity is required")
        @Min(value = 1, message = "Room capacity must be at least 1")
        Integer capacity

) {}