package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return symptomService.getAllSymptoms();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Symptom>> searchSymptoms(@RequestParam String name) {
        List<Symptom> symptoms = symptomService.searchSymptomsByName(name);
        return symptoms.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(symptoms);
    }
}