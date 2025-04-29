package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
    List<Appointment> findByUserIdOrderByAppointmentTimeDesc(Long userId);
    List<Appointment> findByScheduleId(Long scheduleId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentTime >= :now ORDER BY a.appointmentTime")
    List<Appointment> findUpcomingByDoctorId(@Param("doctorId") Long doctorId, @Param("now") LocalDateTime now);
}