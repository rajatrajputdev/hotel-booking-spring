package net.rajatrajputdev.hotelbooking.models;

import net.rajatrajputdev.hotelbooking.enums.PaymentMethod;
import net.rajatrajputdev.hotelbooking.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "payments")
public class PaymentModel {

    @Id
    private ObjectId paymentId;

    @DecimalMin(
            value = "0.00",
            inclusive = false,
            message = "Payment amount must be greater than zero"
    )
    @NotNull(message = "Payment amount is required")
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotBlank(message = "Payment method is required")
    private PaymentMethod paymentMethod;


    @NotBlank(message = "Payment status is required")
    private PaymentStatus status;

    @NotNull(message = "Booking ID is required")
    private ObjectId bookingId;
}
