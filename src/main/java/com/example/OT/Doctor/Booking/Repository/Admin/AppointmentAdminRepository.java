package com.example.OT.Doctor.Booking.Repository.Admin;


import com.example.OT.Doctor.Booking.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentAdminRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllAppointments();

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentDate) = :date")
    List<Appointment> findByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND DATE(a.appointmentDate) = :date")
    List<Appointment> findByDoctorIdAndDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);}