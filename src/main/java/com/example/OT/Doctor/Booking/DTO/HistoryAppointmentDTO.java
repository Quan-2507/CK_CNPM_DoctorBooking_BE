package com.example.OT.Doctor.Booking.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class HistoryAppointmentDTO {
    private Integer appointmentNumber;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public HistoryAppointmentDTO(Integer appointmentNumber, LocalDateTime appointmentDateTime) {
        this.appointmentNumber = appointmentNumber;
        this.appointmentDate = appointmentDateTime.toLocalDate();
        this.appointmentTime = appointmentDateTime.toLocalTime().withSecond(0).withNano(0);
    }

    // Getters
    public Integer getAppointmentNumber() {
        return appointmentNumber;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }
}
