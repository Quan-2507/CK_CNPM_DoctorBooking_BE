package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Long> {
}