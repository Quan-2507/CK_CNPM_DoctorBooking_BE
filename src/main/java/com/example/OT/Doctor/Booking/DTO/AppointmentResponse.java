package com.example.OT.Doctor.Booking.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder

public class AppointmentResponse {
    private Integer id;
    private Integer userId;
    private Integer doctorId;
    private Date appointmentDate;
    private String status;
    private Date createdAt;
}
