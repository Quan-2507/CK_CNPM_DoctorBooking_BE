package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }
}
