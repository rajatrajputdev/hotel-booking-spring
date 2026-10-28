package net.rajatrajputdev.hotelbooking.dto.response;

import net.rajatrajputdev.hotelbooking.enums.PaymentMethod;
import net.rajatrajputdev.hotelbooking.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(
        String paymentId,
        BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        String bookingId
) {}