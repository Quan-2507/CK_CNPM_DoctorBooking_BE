package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRemainingSeatsGreaterThan(int remainingSeats);

    List<Schedule> findByDoctorIdAndRemainingSeatsGreaterThan(Long doctorId, Integer remainingSeats);
}