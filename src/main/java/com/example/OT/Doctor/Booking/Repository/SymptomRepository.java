package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByNameEnContainingIgnoreCase(String nameEn);
    List<Symptom> findByNameViContainingIgnoreCase(String nameVi);
}