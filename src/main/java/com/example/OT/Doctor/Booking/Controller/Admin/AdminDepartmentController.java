package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Service.Admin.AdminDepartmentService;
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
@RequestMapping("/api/admin/departments")
@CrossOrigin(origins = "*")
public class AdminDepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDepartmentController.class);

    @Autowired
    private AdminDepartmentService adminDepartmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Thêm phòng ban mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thêm phòng ban thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc tên phòng ban đã tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentCreateRequestDTO request) {
        logger.info("Received request to create department with nameEn: {}", request.getNameEn());
        try {
            DepartmentResponseDTO response = adminDepartmentService.createDepartment(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật thông tin phòng ban")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật phòng ban thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ, phòng ban không tồn tại, hoặc tên phòng ban đã tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentUpdateRequestDTO request) {
        logger.info("Received request to update department with ID: {}", id);
        try {
            DepartmentResponseDTO response = adminDepartmentService.updateDepartment(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa phòng ban")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa phòng ban thành công"),
            @ApiResponse(responseCode = "400", description = "Phòng ban không tồn tại hoặc có bác sĩ liên kết"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        logger.info("Received request to delete department with ID: {}", id);
        try {
            adminDepartmentService.deleteDepartment(id);
            return ResponseEntity.ok("Xóa phòng ban thành công");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách phòng ban")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách phòng ban thành công"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        logger.info("Received request to fetch all departments");
        List<DepartmentResponseDTO> departments = adminDepartmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
}