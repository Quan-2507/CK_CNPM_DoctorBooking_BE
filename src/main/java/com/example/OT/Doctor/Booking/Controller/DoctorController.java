package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.DoctorDTO;
import com.example.OT.Doctor.Booking.DTO.DoctorDetailDTO;
import com.example.OT.Doctor.Booking.DTO.PrescriptionRequestDTO;
import com.example.OT.Doctor.Booking.DTO.PrescriptionResponseDTO;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Service.DoctorService;
import com.example.OT.Doctor.Booking.Service.PrescriptionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PrescriptionService prescriptionService;

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    @PostMapping("/prescriptions")
    @PreAuthorize("hasRole('DOCTOR')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kê đơn thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc không đủ tồn kho"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền kê đơn")
    })
    public ResponseEntity<?> createPrescription(@Valid @RequestBody PrescriptionRequestDTO request) {
        logger.info("Received POST request for /api/doctors/prescriptions: {}", request);
        try {
            PrescriptionResponseDTO response = prescriptionService.createPrescription(request);
            logger.info("Prescription created successfully with ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating prescription: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> getDoctorsByName(@RequestParam String name) {
        List<Doctor> doctors = doctorService.getDoctorsByName(name);
        if (doctors.isEmpty()) {
            return ResponseEntity.status(404).body("No doctors found");
        }
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("/department/{departmentId}")
    public List<DoctorDTO> getDoctorsByDepartment(@PathVariable Long departmentId) {
        return doctorService.getDoctorsByDepartmentId(departmentId); // Trả về danh sách DoctorDTO
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            DoctorDetailDTO doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}


