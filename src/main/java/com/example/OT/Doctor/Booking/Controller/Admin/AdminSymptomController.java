package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/symptoms")
@CrossOrigin(origins = "*")
public class AdminSymptomController {

    @Autowired
    private SymptomRepository symptomRepository;

    @GetMapping
    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }
}