package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // Tìm tất cả lịch còn chỗ trống
    List<Schedule> findByRemainingSeatsGreaterThan(int remainingSeats);

    // Tìm lịch còn chỗ trống theo doctorId
    @Query("SELECT s FROM Schedule s WHERE s.doctorId = :doctorId AND s.remainingSeats > 0 ORDER BY s.time")
    List<Schedule> findByDoctorIdAndRemainingSeatsGreaterThan(@Param("doctorId") Integer doctorId);

    // Có thể thêm phương thức query method của JPA
    List<Schedule> findByDoctorIdAndRemainingSeatsGreaterThanOrderByTime(Integer doctorId, int remainingSeats);
}