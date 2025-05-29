package com.example.OT.Doctor.Booking.DTO.Admin;

import com.example.OT.Doctor.Booking.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private Long departmentId;
    private String departmentName;
    private String phoneNumber;
    private String email;
    private Integer experienceYears;
    private String degree;
    private Integer consultationHours;
    private Float rating;
    private String about;
    private String imageUrl;
    private Long userId;
}
