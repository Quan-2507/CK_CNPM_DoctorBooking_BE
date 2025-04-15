package com.example.OT.Doctor.Booking.Enum;

public enum Role {
    // Phải có prefix "ROLE_" để tương thích Spring Security
    ROLE_PATIENT,
    ROLE_DOCTOR,
    ROLE_ADMIN;
}
