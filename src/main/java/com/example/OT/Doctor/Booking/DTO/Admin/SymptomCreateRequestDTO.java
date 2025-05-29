package com.example.OT.Doctor.Booking.DTO.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SymptomCreateRequestDTO {
    @NotBlank(message = "Tên triệu chứng không được để trống")
    private String nameEn;
    @NotBlank(message = "Tên triệu chứng không được để trống")
    private String nameVi;
    private String description;
}