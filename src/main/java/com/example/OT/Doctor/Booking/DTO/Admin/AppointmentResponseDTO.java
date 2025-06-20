package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime appointmentDate;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String doctorName;
    private Integer appointmentNumber;
    private String userName;
}