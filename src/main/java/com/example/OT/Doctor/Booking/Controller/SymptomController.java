package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @GetMapping
    public List<Symptom> getAllSymptoms() {
        return symptomService.getAllSymptoms();    }
}

