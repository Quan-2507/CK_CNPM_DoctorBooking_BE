package com.example.OT.Doctor.Booking.DTO.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorCreateRequestDTO {
    @NotBlank(message = "Tên bác sĩ không được để trống")
    private String name;

    @NotNull(message = "ID phòng ban không được để trống")
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