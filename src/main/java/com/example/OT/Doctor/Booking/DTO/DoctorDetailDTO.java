package com.example.OT.Doctor.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDetailDTO {
    private Long id;
    private String name;
    private int experienceYears;
    private String degree;
    private int consultationHours;
    private String imageUrl;
    private String departmentNameVi;
    private String departmentNameEn;

    // Constructor
    public DoctorDetailDTO(Long id, String name, int experienceYears, String degree, int consultationHours, String imageUrl, String departmentNameVi, String departmentNameEn) {
        this.id = id;
        this.name = name;
        this.experienceYears = experienceYears;
        this.degree = degree;
        this.consultationHours = consultationHours;
        this.imageUrl = imageUrl;
        this.departmentNameVi = departmentNameVi;
        this.departmentNameEn = departmentNameEn;
    }


}