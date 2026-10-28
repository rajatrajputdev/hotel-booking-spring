package net.rajatrajputdev.hotelbooking.dto.response;

import net.rajatrajputdev.hotelbooking.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public record BookingResponse(
        String bookingId,
        LocalDate checkIn,
        LocalDate checkOut,
        BookingStatus status,
        Integer amount,
        List<String> guestIds,
        String staffId
) {}