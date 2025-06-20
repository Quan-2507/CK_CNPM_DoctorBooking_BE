package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Repository.Admin.DepartmentAdminRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(AdminDepartmentService.class);

    @Autowired
    private DepartmentAdminRepository departmentRepository;

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentCreateRequestDTO request) {
        logger.info("Creating department with nameEn: {}", request.getNameEn());

        if (departmentRepository.existsByNameEn(request.getNameEn())) {
            logger.error("Department already exists with nameEn: {}", request.getNameEn());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameEn());
        }

        if (request.getNameVi() != null && departmentRepository.existsByNameVi(request.getNameVi())) {
            logger.error("Department already exists with nameVi: {}", request.getNameVi());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameVi());
        }

        Department department = new Department();
        department.setNameEn(request.getNameEn());
        department.setNameVi(request.getNameVi());
        department.setIsActive(1); // Mặc định là hoạt động

        Department savedDepartment = departmentRepository.save(department);
        logger.info("Saved department with ID: {}", savedDepartment.getId());

        return mapToDepartmentResponseDTO(savedDepartment);
    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentUpdateRequestDTO request) {
        logger.info("Updating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", id);
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + id);
                });

        if (!request.getNameEn().equals(department.getNameEn()) && departmentRepository.existsByNameEn(request.getNameEn())) {
            logger.error("Department already exists with nameEn: {}", request.getNameEn());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameEn());
        }

        if (request.getNameVi() != null && !request.getNameVi().equals(department.getNameVi()) && departmentRepository.existsByNameVi(request.getNameVi())) {
            logger.error("Department already exists with nameVi: {}", request.getNameVi());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameVi());
        }

        department.setNameEn(request.getNameEn());
        department.setNameVi(request.getNameVi());

        Department updatedDepartment = departmentRepository.save(department);
        logger.info("Updated department with ID: {}", updatedDepartment.getId());

        return mapToDepartmentResponseDTO(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        logger.info("Deactivating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", id);
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + id);
                });

        if (department.getDoctors().stream().anyMatch(doctor -> doctor.getIsActive() == 1)) {
            logger.error("Cannot deactivate department with ID: {} because it has active associated doctors", id);
            throw new IllegalArgumentException("Không thể vô hiệu hóa phòng ban vì có bác sĩ hoạt động liên kết");
        }

        department.setIsActive(0); // Chuyển trạng thái thành không hoạt động
        departmentRepository.save(department);
        logger.info("Deactivated department with ID: {}", id);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        logger.info("Fetching all active departments");

        List<Department> departments = departmentRepository.findByIsActive(1);
        return departments.stream()
                .map(this::mapToDepartmentResponseDTO)
                .collect(Collectors.toList());
    }

    private DepartmentResponseDTO mapToDepartmentResponseDTO(Department department) {
        DepartmentResponseDTO response = new DepartmentResponseDTO();
        response.setId(department.getId());
        response.setNameEn(department.getNameEn());
        response.setNameVi(department.getNameVi());
        return response;
    }
}