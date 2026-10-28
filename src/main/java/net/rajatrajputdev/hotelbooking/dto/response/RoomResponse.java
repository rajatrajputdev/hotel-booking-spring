package net.rajatrajputdev.hotelbooking.dto.response;

import net.rajatrajputdev.hotelbooking.enums.RoomStatus;

public record RoomResponse(
        String roomId,
        int roomNumber,
        RoomStatus status,
        String roomTypeId
) {}