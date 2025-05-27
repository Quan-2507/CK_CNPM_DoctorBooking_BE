package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DoctorResponseDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String username;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private Long departmentId;
    private String departmentName;
    private String degree;
    private Integer experienceYears;
    private Integer consultationHours;
    private Float rating;
    private String about;
    private String imageUrl;
}