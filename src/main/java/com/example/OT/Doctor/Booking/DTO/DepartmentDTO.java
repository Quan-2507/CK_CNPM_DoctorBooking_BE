package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.Department;

import lombok.Getter;


@Getter

public class DepartmentDTO {

    private final Long id;
    private final String nameVi;
    private final String nameEn;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.nameVi = department.getNameVi();
        this.nameEn = department.getNameEn();
    }
}