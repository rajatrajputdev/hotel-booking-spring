package net.rajatrajputdev.hotelbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import net.rajatrajputdev.hotelbooking.enums.RoomStatus;
import org.bson.types.ObjectId;

public record RoomRequest(

        @Positive(message = "Room number must be greater than zero")
        int roomNumber,

        @NotNull(message = "Room status is required")
        RoomStatus status,

        @NotNull(message = "Room type ID is required")
        ObjectId roomTypeId

) {}