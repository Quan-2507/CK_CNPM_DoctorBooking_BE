package com.example.OT.Doctor.Booking.DTO.Admin;

import com.example.OT.Doctor.Booking.Entity.User;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorUpdateRequestDTO {
    private String name;

    private Long departmentId;

    private String phoneNumber;

    @Email(message = "Email không hợp lệ")
    private String email;

    private Integer experienceYears;

    private String degree;

    private Integer consultationHours;

    private Float rating;

    private String about;

    private String imageUrl;

    private Long userId;
}