package net.rajatrajputdev.hotelbooking.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import net.rajatrajputdev.hotelbooking.enums.PaymentMethod;
import net.rajatrajputdev.hotelbooking.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(

        @NotNull(message = "Payment amount is required")
        @DecimalMin(value = "0.00", inclusive = false, message = "Payment amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment date is required")
        LocalDate paymentDate,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Payment status is required")
        PaymentStatus status,

        @NotNull(message = "Booking ID is required")
        String bookingId

) {}