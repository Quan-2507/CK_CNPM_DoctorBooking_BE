package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DepartmentWithDoctorsDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Enum.Role;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
import com.example.OT.Doctor.Booking.Repository.Admin.DoctorAdminRepository;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDoctorService {

    private static final Logger logger = LoggerFactory.getLogger(AdminDoctorService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorAdminRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public DoctorResponseDTO createDoctor(DoctorCreateRequestDTO request) {
        logger.info("Creating doctor with user_id: {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", request.getUserId());
                    return new IllegalArgumentException("User không tồn tại với ID: " + request.getUserId());
                });

        if (user.getRole() != Role.DOCTOR) {
            logger.info("Changing user role from {} to DOCTOR for user ID: {}", user.getRole(), request.getUserId());
            user.setRole(Role.DOCTOR);
            userRepository.save(user);
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", request.getDepartmentId());
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + request.getDepartmentId());
                });

        if (doctorRepository.findByUserId(request.getUserId()).isPresent()) {
            logger.error("Doctor already exists for user ID: {}", request.getUserId());
            throw new IllegalArgumentException("Bác sĩ đã tồn tại cho user ID: " + request.getUserId());
        }

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setName(request.getName());
        doctor.setDepartment(department);
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setEmail(request.getEmail());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setDegree(request.getDegree());
        doctor.setConsultationHours(request.getConsultationHours());
        doctor.setRating(request.getRating());
        doctor.setAbout(request.getAbout());
        doctor.setImageUrl(request.getImageUrl());

        Doctor savedDoctor = doctorRepository.save(doctor);
        logger.info("Saved doctor with ID: {}", savedDoctor.getId());

        return mapToDoctorResponseDTO(savedDoctor);
    }

    @Transactional
    public DoctorResponseDTO getDoctorById(Long id) {
        logger.info("Fetching doctor with ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with ID: {}", id);
                    return new IllegalArgumentException("Bác sĩ không tồn tại với ID: " + id);
                });

        return mapToDoctorResponseDTO(doctor);
    }

    @Transactional
    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO request) {
        logger.info("Updating doctor with ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with ID: {}", id);
                    return new IllegalArgumentException("Bác sĩ không tồn tại với ID: " + id);
                });

        // Kiểm tra user_id nếu thay đổi
        if (request.getUserId() != null && !request.getUserId().equals(doctor.getUser().getId())) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", request.getUserId());
                        return new IllegalArgumentException("User không tồn tại với ID: " + request.getUserId());
                    });

            if (user.getRole() != Role.DOCTOR) {
                logger.info("Changing user role from {} to DOCTOR for user ID: {}", user.getRole(), request.getUserId());
                user.setRole(Role.DOCTOR);
                userRepository.save(user);
            }

            if (doctorRepository.findByUserId(request.getUserId()).isPresent()) {
                logger.error("Doctor already exists for user ID: {}", request.getUserId());
                throw new IllegalArgumentException("Bác sĩ đã tồn tại cho user ID: " + request.getUserId());
            }

            doctor.setUser(user);
        }

        // Kiểm tra và cập nhật department
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> {
                        logger.error("Department not found with ID: {}", request.getDepartmentId());
                        return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + request.getDepartmentId());
                    });
            doctor.setDepartment(department);
        }

        // Cập nhật các trường khác nếu có
        if (request.getName() != null) doctor.setName(request.getName());
        if (request.getPhoneNumber() != null) doctor.setPhoneNumber(request.getPhoneNumber());
        if (request.getEmail() != null) doctor.setEmail(request.getEmail());
        if (request.getExperienceYears() != null) doctor.setExperienceYears(request.getExperienceYears());
        if (request.getDegree() != null) doctor.setDegree(request.getDegree());
        if (request.getConsultationHours() != null) doctor.setConsultationHours(request.getConsultationHours());
        if (request.getRating() != null) doctor.setRating(request.getRating());
        if (request.getAbout() != null) doctor.setAbout(request.getAbout());
        if (request.getImageUrl() != null) doctor.setImageUrl(request.getImageUrl());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        logger.info("Updated doctor with ID: {}", updatedDoctor.getId());

        return mapToDoctorResponseDTO(updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        logger.info("Deleting doctor with ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with ID: {}", id);
                    return new IllegalArgumentException("Bác sĩ không tồn tại với ID: " + id);
                });

        User user = doctor.getUser();
        if (user != null && user.getRole() == Role.DOCTOR) {
            logger.info("Changing user role from DOCTOR to PATIENT for user ID: {}", user.getId());
            user.setRole(Role.PATIENT);
            userRepository.save(user);
        }

        doctorRepository.delete(doctor);
        logger.info("Deleted doctor with ID: {}", id);
    }

    public List<DepartmentWithDoctorsDTO> getAllDoctors() {
        logger.info("Fetching doctors grouped by department");

        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(department -> {
            DepartmentWithDoctorsDTO dto = new DepartmentWithDoctorsDTO();
            dto.setDepartmentId(department.getId());
            dto.setDepartmentName(department.getNameEn());

            List<DoctorResponseDTO> doctors = doctorRepository.findByDepartment(department)
                    .stream()
                    .map(this::mapToDoctorResponseDTO)
                    .filter(response -> response != null)
                    .collect(Collectors.toList());

            dto.setDoctors(doctors);
            return dto;
        }).collect(Collectors.toList());
    }

    private DoctorResponseDTO mapToDoctorResponseDTO(Doctor doctor) {
        if (doctor.getUser() == null) {
            logger.warn("Doctor with ID: {} has null User. Skipping.", doctor.getId());
            return null;
        }

        DoctorResponseDTO response = new DoctorResponseDTO();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setDepartmentId(doctor.getDepartment().getId());
        response.setDepartmentName(doctor.getDepartment().getNameEn());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setEmail(doctor.getEmail());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setDegree(doctor.getDegree());
        response.setConsultationHours(doctor.getConsultationHours());
        response.setRating(doctor.getRating());
        response.setAbout(doctor.getAbout());
        response.setImageUrl(doctor.getImageUrl());
        response.setUserId(doctor.getUser().getId());
        return response;
    }
}