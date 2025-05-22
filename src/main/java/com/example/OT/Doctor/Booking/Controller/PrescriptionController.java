package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.PrescriptionDTO;
import com.example.OT.Doctor.Booking.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    /**
     * Lấy chi tiết đơn thuốc theo appointmentId
     * @param appointmentId ID của lịch hẹn
     * @return PrescriptionDTO
     */
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionByAppointmentId(@PathVariable Long appointmentId) {
        PrescriptionDTO prescriptionDTO = prescriptionService.getPrescriptionDetailsByAppointmentId(appointmentId);
        return ResponseEntity.ok(prescriptionDTO);
    }
}
