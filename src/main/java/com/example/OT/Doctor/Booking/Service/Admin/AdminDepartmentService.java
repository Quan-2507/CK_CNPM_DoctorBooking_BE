package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentCreateRequestDTO request) {

        logger.info("Creating department with nameEn: {}", request.getNameEn());

        // Kiểm tra tên phòng ban (tiếng Anh) đã tồn tại
        if (departmentRepository.existsByNameEn(request.getNameEn())) {
            logger.error("Department already exists with nameEn: {}", request.getNameEn());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameEn());
        }
        logger.info("Creating department with nameVi: {}", request.getNameVi());

        // Kiểm tra tên phòng ban (tiếng Anh) đã tồn tại
        if (departmentRepository.existsByNameVi(request.getNameEn())) {
            logger.error("Department already exists with nameEn: {}", request.getNameVi());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameVi());
        }
        // Tạo department
        Department department = new Department();
        department.setNameEn(request.getNameEn());
        department.setNameVi(request.getNameVi());

        Department savedDepartment = departmentRepository.save(department);
        logger.info("Saved department with ID: {}", savedDepartment.getId());

        return mapToDepartmentResponseDTO(savedDepartment);
    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentUpdateRequestDTO request) {
        logger.info("Updating department with ID: {}", id);

        // Tìm department
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", id);
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + id);
                });

        // Kiểm tra tên phòng ban (tiếng Anh) nếu thay đổi
        if (!request.getNameEn().equals(department.getNameEn()) && departmentRepository.existsByNameEn(request.getNameEn())) {
            logger.error("Department already exists with nameEn: {}", request.getNameEn());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameEn());
        }
        // Kiểm tra tên phòng ban (tiếng Việt) nếu thay đổi
        if (!request.getNameVi().equals(department.getNameVi()) && departmentRepository.existsByNameVi(request.getNameVi())) {
            logger.error("Department already exists with nameVi: {}", request.getNameVi());
            throw new IllegalArgumentException("Phòng ban đã tồn tại với tên: " + request.getNameVi());
        }

        // Cập nhật department
        department.setNameEn(request.getNameEn());
        department.setNameVi(request.getNameVi());

        Department updatedDepartment = departmentRepository.save(department);
        logger.info("Updated department with ID: {}", updatedDepartment.getId());

        return mapToDepartmentResponseDTO(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        logger.info("Deleting department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", id);
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + id);
                });

        // Kiểm tra xem phòng ban có bác sĩ nào không
        if (!department.getDoctors().isEmpty()) {
            logger.error("Cannot delete department with ID: {} because it has associated doctors", id);
            throw new IllegalArgumentException("Không thể xóa phòng ban vì có bác sĩ liên kết");
        }

        departmentRepository.delete(department);
        logger.info("Deleted department with ID: {}", id);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        logger.info("Fetching all departments");

        List<Department> departments = departmentRepository.findAll();
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