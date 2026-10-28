package net.rajatrajputdev.hotelbooking.dto.response;

public record RoomTypeResponse(
        String roomTypeId,
        String name,
        String description,
        Integer pricePerNight,
        Integer capacity
) {}