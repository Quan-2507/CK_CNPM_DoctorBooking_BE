package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.AppointmentResponseDTO;
import com.example.OT.Doctor.Booking.Service.Admin.AdminAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/appointments")
public class AdminAppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AdminAppointmentController.class);

    @Autowired
    private AdminAppointmentService adminAppointmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách lịch hẹn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách lịch hẹn thành công"),
            @ApiResponse(responseCode = "400", description = "Định dạng ngày không hợp lệ"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> getAppointments(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String date) {
        logger.info("Received request to fetch appointments with doctorId: {} and date: {}", doctorId, date);
        try {
            LocalDate parsedDate = date != null ? LocalDate.parse(date) : null;
            List<AppointmentResponseDTO> appointments = adminAppointmentService.getAppointments(doctorId, parsedDate);
            return ResponseEntity.ok(appointments);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", date);
            return ResponseEntity.badRequest().body("Định dạng ngày không hợp lệ, sử dụng YYYY-MM-DD");
        }
    }
}