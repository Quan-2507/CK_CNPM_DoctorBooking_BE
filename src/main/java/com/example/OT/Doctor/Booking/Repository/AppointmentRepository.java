package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> { // Đổi thành Integer
    boolean existsByDoctorIdAndAppointmentDate(Integer doctorId, Date appointmentDate);

    List<Appointment> findByUserIdOrderByAppointmentDateDesc(Integer userId); // Đổi thành Integer

    @Query("SELECT a FROM Appointment a WHERE a.doctorId = :doctorId AND a.appointmentDate >= :now ORDER BY a.appointmentDate")
    List<Appointment> findUpcomingByDoctorId(@Param("doctorId") Integer doctorId, @Param("now") LocalDateTime now);
}