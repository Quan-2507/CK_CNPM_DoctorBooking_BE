package com.example.OT.Doctor.Booking.Controller;


import com.example.OT.Doctor.Booking.DTO.AppointmentResponse;
import com.example.OT.Doctor.Booking.DTO.BookAppointmentRequest;
import com.example.OT.Doctor.Booking.Service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class AppointmentController {

    private final AppointmentService bookingService;

    public AppointmentController(AppointmentService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @Valid @RequestBody BookAppointmentRequest request) {
        AppointmentResponse response = bookingService.bookAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<AppointmentResponse>> getUserAppointments(
            @PathVariable Integer userId) {
        List<AppointmentResponse> appointments = bookingService.getUserAppointments(userId);
        return appointments.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(appointments);
    }
}