package com.example.OT.Doctor.Booking.DTO.Admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentCreateRequestDTO {
    @NotBlank(message = "Tên phòng ban (tiếng Anh) không được để trống")
    private String nameEn;

    private String nameVi;


}