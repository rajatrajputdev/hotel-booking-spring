package net.rajatrajputdev.hotelbooking.dto.response;

public record GuestResponse(
        String guestId,
        String name,
        String email,
        String phone
) {}