package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponseDTO {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private String departmentName;
    private String date;
    private String startTime;
    private String endTime;
    private Integer remainingSeats;
    private Integer numOfSeats;
}
