package com.example.OT.Doctor.Booking.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistoryAppointmentDTO {
    private Integer appointmentNumber;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public HistoryAppointmentDTO(Integer appointmentNumber, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.appointmentNumber = appointmentNumber;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime.withSecond(0).withNano(0);
    }

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
