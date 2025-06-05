package com.example.OT.Doctor.Booking.DTO.Admin;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SymptomUpdateRequestDTO {
    private String nameEn;
    private String nameVi;
    private String description;
}