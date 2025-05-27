package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DoctorCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Service.Admin.AdminDoctorService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/admin/doctors")
public class AdminDoctorController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDoctorController.class);

    @Autowired
    private AdminDoctorService adminDoctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Thêm bác sĩ mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thêm bác sĩ thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ, user_id không tồn tại, hoặc đã có bác sĩ liên kết với user_id"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorCreateRequestDTO request) {
        logger.info("Received request to create doctor with user_id: {}", request.getUserId());
        try {
            DoctorResponseDTO response = adminDoctorService.createDoctor(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating doctor: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật thông tin bác sĩ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật bác sĩ thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ, bác sĩ không tồn tại, hoặc user_id đã liên kết với bác sĩ khác"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateRequestDTO request) {
        logger.info("Received request to update doctor with ID: {}", id);
        try {
            DoctorResponseDTO response = adminDoctorService.updateDoctor(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating doctor: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa bác sĩ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa bác sĩ thành công"),
            @ApiResponse(responseCode = "400", description = "Bác sĩ không tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        logger.info("Received request to delete doctor with ID: {}", id);
        try {
            adminDoctorService.deleteDoctor(id);
            return ResponseEntity.ok("Xóa bác sĩ thành công");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting doctor: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách bác sĩ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách bác sĩ thành công"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        logger.info("Received request to fetch all doctors");
        List<DoctorResponseDTO> doctors = adminDoctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
}