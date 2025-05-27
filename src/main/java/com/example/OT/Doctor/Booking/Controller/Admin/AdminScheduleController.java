package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.ScheduleRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.ScheduleResponseDTO;
import com.example.OT.Doctor.Booking.Service.Admin.AdminScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/schedules")
public class AdminScheduleController {

    private final AdminScheduleService adminScheduleService;

    public AdminScheduleController(AdminScheduleService adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@Valid @RequestBody ScheduleRequestDTO request) {
        try {
            ScheduleResponseDTO schedule = adminScheduleService.createSchedule(request);
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }

}
