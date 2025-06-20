package com.example.OT.Doctor.Booking.Repository.Admin;
import com.example.OT.Doctor.Booking.Entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomAdminRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByIsActive(Integer isActive);
}