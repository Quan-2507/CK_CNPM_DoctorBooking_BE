package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.SymptomDTO;
import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    public List<SymptomDTO> searchSymptomsByName(String name) {
        // Tìm kiếm theo nameVi
        List<Symptom> symptoms = symptomRepository.findByNameViContainingIgnoreCase(name);
        return symptoms.stream()
                .map(this::mapToSymptomDTO)
                .collect(Collectors.toList());
    }

    private SymptomDTO mapToSymptomDTO(Symptom symptom) {
        return new SymptomDTO(
                symptom.getId(),
                symptom.getNameEn(),
                symptom.getNameVi(),
                symptom.getDescription()
        );
    }
}