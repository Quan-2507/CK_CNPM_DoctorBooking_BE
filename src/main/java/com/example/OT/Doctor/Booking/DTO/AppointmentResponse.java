package com.example.OT.Doctor.Booking.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private Long userId;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime appointmentTime;
    private Integer appointmentNumber; // Số thứ tự
    private String status;
    private LocalDateTime createdAt;
}