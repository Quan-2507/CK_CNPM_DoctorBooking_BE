package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.SymptomCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.SymptomResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.SymptomUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Service.Admin.AdminSymptomService;
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
@RequestMapping("/api/admin/symptoms")
@CrossOrigin(origins = "*")
public class AdminSymptomController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSymptomController.class);

    @Autowired
    private AdminSymptomService adminSymptomService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Thêm triệu chứng mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thêm triệu chứng thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc phòng ban không tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> createSymptom(@Valid @RequestBody SymptomCreateRequestDTO request) {
        logger.info("Received request to create symptom with name: {}", request.getNameEn());
        logger.info("Received request to create symptom with name: {}", request.getNameVi());
        try {
            SymptomResponseDTO response = adminSymptomService.createSymptom(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating symptom: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật thông tin triệu chứng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật triệu chứng thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ, triệu chứng không tồn tại, hoặc phòng ban không tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> updateSymptom(@PathVariable Long id, @Valid @RequestBody SymptomUpdateRequestDTO request) {
        logger.info("Received request to update symptom with ID: {}", id);
        try {
            SymptomResponseDTO response = adminSymptomService.updateSymptom(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating symptom: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa triệu chứng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa triệu chứng thành công"),
            @ApiResponse(responseCode = "400", description = "Triệu chứng không tồn tại"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<?> deleteSymptom(@PathVariable Long id) {
        logger.info("Received request to delete symptom with ID: {}", id);
        try {
            adminSymptomService.deleteSymptom(id);
            return ResponseEntity.ok("Xóa triệu chứng thành công");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting symptom: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách triệu chứng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách triệu chứng thành công"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện")
    })
    public ResponseEntity<List<SymptomResponseDTO>> getAllSymptoms() {
        logger.info("Received request to fetch all symptoms");
        List<SymptomResponseDTO> symptoms = adminSymptomService.getAllSymptoms();
        return ResponseEntity.ok(symptoms);
    }
}