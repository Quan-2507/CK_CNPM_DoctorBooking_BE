package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import lombok.Getter;

@Getter
public class SymptomDTO {
    private Long id;
    private String nameEn;
    private String nameVi;
    private String description;

    public SymptomDTO(Long id, String nameEn, String nameVi, String description) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameVi = nameVi;
        this.description = description;
    }
}