package models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "booking_rooms")
public class BookingRoomModel {

    @Id
    private ObjectId bookingRoomId;

    @NotNull(message = "Booking ID is required")
    private ObjectId bookingId;

    @NotNull(message = "Room ID is required")
    private ObjectId roomId;
}