package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.Entity.Schedule;
import com.example.OT.Doctor.Booking.Service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<Schedule>> getAllAvailableSchedules() {
        List<Schedule> schedules = scheduleService.getAllAvailableSchedules();
        return schedules.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(schedules);
    }

    @GetMapping("/doctors/{doctorId}/available")
    public ResponseEntity<List<Schedule>> getAvailableSchedulesByDoctor(
            @PathVariable Long doctorId) {
        List<Schedule> schedules = scheduleService.getAvailableSchedulesByDoctor(doctorId);
        return schedules.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(schedules);
    }
}