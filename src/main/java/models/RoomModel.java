package models;

import enums.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rooms")
public class RoomModel {

    @Id
    private ObjectId roomId;

    @Positive(message = "Room number must be greater than zero")
    private int roomNumber;

    @NotBlank(message = "Room status is required")
    private RoomStatus status;

    @NotNull(message = "Room type ID is required")
    private ObjectId roomTypeId;
}