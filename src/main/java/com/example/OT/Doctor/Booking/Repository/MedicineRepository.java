package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
