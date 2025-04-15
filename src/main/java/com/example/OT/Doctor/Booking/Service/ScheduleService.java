package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Entity.Schedule;
import com.example.OT.Doctor.Booking.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // Sử dụng constructor injection thay cho @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // Lấy tất cả lịch còn chỗ trống
    public List<Schedule> getAllAvailableSchedules() {
        return scheduleRepository.findByRemainingSeatsGreaterThan(0);
    }

    // Lấy lịch còn chỗ trống theo doctorId
    public List<Schedule> getAvailableSchedulesByDoctor(Integer doctorId) {
        return scheduleRepository.findByDoctorIdAndRemainingSeatsGreaterThan(doctorId);
        // Hoặc có thể dùng:
        // return scheduleRepository.findByDoctorIdAndRemainingSeatsGreaterThanOrderByTime(doctorId, 0);
    }
}