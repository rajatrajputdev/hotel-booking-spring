package net.rajatrajputdev.hotelbooking.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StaffRequest(

        @NotBlank(message = "Staff name is required")
        String name,

        @NotBlank(message = "Staff role is required")
        String role

) {}