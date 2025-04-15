package com.example.OT.Doctor.Booking.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookAppointmentRequest {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Doctor ID is required")
    private Integer doctorId;

    @NotNull(message = "Appointment date is required")
    private Date appointmentDate;
}

