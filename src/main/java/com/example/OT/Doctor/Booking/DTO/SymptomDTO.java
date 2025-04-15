package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import lombok.Getter;

@Getter
public class SymptomDTO {
    private final String name;
    private final String description;

    public SymptomDTO(Symptom symptom) {
        this.name = symptom.getName();
        this.description = symptom.getDescription();
    }

}

