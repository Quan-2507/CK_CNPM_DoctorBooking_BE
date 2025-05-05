package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Entity.Schedule;
import com.example.OT.Doctor.Booking.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllAvailableSchedules() {
        return scheduleRepository.findByRemainingSeatsGreaterThan(0);
    }

    public List<Schedule> getAvailableSchedulesByDoctor(Long doctorId) {
        return scheduleRepository.findByDoctorIdAndRemainingSeatsGreaterThan(doctorId, 0);
    }
}