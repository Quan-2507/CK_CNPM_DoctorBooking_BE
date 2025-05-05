package com.example.OT.Doctor.Booking.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookAppointmentRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    @NotNull(message = "Appointment time is required")
    private LocalDateTime appointmentTime;

    private String symptomDescription;
    private Long diseaseId;
}