package com.example.OT.Doctor.Booking.DTO.Admin;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorUpdateRequestDTO {
    @Email(message = "Email không hợp lệ")
    private String email;

    private String phoneNumber;

    private String fullName;

    private String gender;

    private LocalDate dateOfBirth;

    private String address;

    private Long departmentId;

    private String degree;

    private Integer experienceYears;

    private Integer consultationHours;

    private Float rating;

    private String about;

    private String imageUrl;
}