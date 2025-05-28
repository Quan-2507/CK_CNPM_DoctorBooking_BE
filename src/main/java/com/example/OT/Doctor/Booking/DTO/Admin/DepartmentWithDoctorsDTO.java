package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartmentWithDoctorsDTO {
    private Long departmentId;
    private String departmentName;
    private List<DoctorResponseDTO> doctors;
}