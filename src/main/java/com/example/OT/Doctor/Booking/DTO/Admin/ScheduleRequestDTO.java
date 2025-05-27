package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDTO {
    private Long doctorId;
    private String date;        // yyyy-MM-dd
    private String startTime;   // HH:mm
    private String endTime;     // HH:mm
    private Integer numOfSeats;
}
