package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Prescription;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT p FROM Prescription p JOIN FETCH p.doctor WHERE p.appointment.id = :appointmentId AND p.status = 'ACTIVE'")
    Optional<Prescription> findActiveByAppointmentIdWithDoctor(@Param("appointmentId") Long appointmentId);


}