package models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room_types")
public class RoomTypeModel {

    @Id
    private ObjectId roomTypeId;

    @NotBlank(message = "Room type name is required")
    private String name;

    @NotBlank(message = "Room type description is required")
    private String description;

    @NotNull(message = "Price per night is required")
    @Min(value = 1, message = "Price per night must be greater than zero")
    private Integer pricePerNight;

    @NotNull(message = "Room capacity is required")
    @Min(value = 1, message = "Room capacity must be at least 1")
    private Integer capacity;
}