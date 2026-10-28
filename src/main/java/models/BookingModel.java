package models;

import enums.BookingStatus;
import jakarta.validation.constraints.*;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "bookings")
public class BookingModel {

    @Id
    private ObjectId bookingId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date cannot be in the past")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOut;

    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    @NotNull(message = "Booking amount is required")
    @Min(value = 1, message = "Booking amount must be greater than zero")
    private Integer amount;

    @NotEmpty(message = "At least one guest is required")
    @DBRef
    private List<GuestModel> guests;

    @DBRef
    private StaffModel staff;
}