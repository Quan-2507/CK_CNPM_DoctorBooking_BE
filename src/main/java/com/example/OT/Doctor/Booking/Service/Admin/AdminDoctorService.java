package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.DoctorCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.DoctorUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
import com.example.OT.Doctor.Booking.Repository.DoctorRepository;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDoctorService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DoctorResponseDTO createDoctor(DoctorCreateRequestDTO request) {
        logger.info("Creating doctor with username: {}", request.getUsername());

        // Kiểm tra email và username đã tồn tại
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.error("Username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra department
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", request.getDepartmentId());
                    return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + request.getDepartmentId());
                });

        // Tạo user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setRole(User.Role.DOCTOR);
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        logger.info("Saved user with ID: {}", savedUser.getId());

        // Tạo doctor
        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setName(request.getFullName() != null ? request.getFullName() : request.getUsername());
        doctor.setDepartment(department);
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setEmail(request.getEmail());
        doctor.setDegree(request.getDegree());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setConsultationHours(request.getConsultationHours());
        doctor.setRating(request.getRating());
        doctor.setAbout(request.getAbout());
        doctor.setImageUrl(request.getImageUrl());

        Doctor savedDoctor = doctorRepository.save(doctor);
        logger.info("Saved doctor with ID: {}", savedDoctor.getId());

        // Tạo response
        return mapToDoctorResponseDTO(savedUser, savedDoctor);
    }

    @Transactional
    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO request) {
        logger.info("Updating doctor with ID: {}", id);

        // Tìm user
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new IllegalArgumentException("User không tồn tại với ID: " + id);
                });

        // Kiểm tra role
        if (user.getRole() != User.Role.DOCTOR) {
            logger.error("User with ID: {} is not a DOCTOR", id);
            throw new IllegalArgumentException("User không phải là bác sĩ");
        }

        // Kiểm tra email nếu thay đổi
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            logger.error("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Tìm doctor
        Doctor doctor = doctorRepository.findByUserId(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found for user ID: {}", id);
                    return new IllegalArgumentException("Bác sĩ không tồn tại cho user ID: " + id);
                });

        // Kiểm tra department nếu thay đổi
        Department department = doctor.getDepartment();
        if (request.getDepartmentId() != null && !request.getDepartmentId().equals(department.getId())) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> {
                        logger.error("Department not found with ID: {}", request.getDepartmentId());
                        return new IllegalArgumentException("Phòng ban không tồn tại với ID: " + request.getDepartmentId());
                    });
        }

        // Cập nhật user
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());
        if (request.getAddress() != null) user.setAddress(request.getAddress());

        User updatedUser = userRepository.save(user);
        logger.info("Updated user with ID: {}", updatedUser.getId());

        // Cập nhật doctor
        doctor.setName(request.getFullName() != null ? request.getFullName() : updatedUser.getUsername());
        doctor.setDepartment(department);
        if (request.getPhoneNumber() != null) doctor.setPhoneNumber(request.getPhoneNumber());
        if (request.getEmail() != null) doctor.setEmail(request.getEmail());
        if (request.getDegree() != null) doctor.setDegree(request.getDegree());
        if (request.getExperienceYears() != null) doctor.setExperienceYears(request.getExperienceYears());
        if (request.getConsultationHours() != null) doctor.setConsultationHours(request.getConsultationHours());
        if (request.getRating() != null) doctor.setRating(request.getRating());
        if (request.getAbout() != null) doctor.setAbout(request.getAbout());
        if (request.getImageUrl() != null) doctor.setImageUrl(request.getImageUrl());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        logger.info("Updated doctor with ID: {}", updatedDoctor.getId());

        return mapToDoctorResponseDTO(updatedUser, updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        logger.info("Deleting doctor with ID: {}", id);

        // Tìm user
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new IllegalArgumentException("User không tồn tại với ID: " + id);
                });

        // Kiểm tra role
        if (user.getRole() != User.Role.DOCTOR) {
            logger.error("User with ID: {} is not a DOCTOR", id);
            throw new IllegalArgumentException("User không phải là bác sĩ");
        }

        // Xóa doctor
        Doctor doctor = doctorRepository.findByUserId(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found for user ID: {}", id);
                    return new IllegalArgumentException("Bác sĩ không tồn tại cho user ID: " + id);
                });

        doctorRepository.delete(doctor);
        logger.info("Deleted doctor with user ID: {}", id);

        // Xóa user
        userRepository.delete(user);
        logger.info("Deleted user with ID: {}", id);
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        logger.info("Fetching all doctors");

        List<User> doctors = userRepository.findByRole(User.Role.DOCTOR);
        return doctors.stream()
                .map(user -> {
                    Doctor doctor = doctorRepository.findByUserId(user.getId())
                            .orElseThrow(() -> new IllegalStateException("Doctor not found for user ID: " + user.getId()));
                    return mapToDoctorResponseDTO(user, doctor);
                })
                .collect(Collectors.toList());
    }

    private DoctorResponseDTO mapToDoctorResponseDTO(User user, Doctor doctor) {
        DoctorResponseDTO response = new DoctorResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setGender(User.Gender.getGender());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setAddress(user.getAddress());
        response.setRole(user.getRole().name());
        response.setStatus(User.Status.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setDepartmentId(doctor.getDepartment().getId());
        response.setDepartmentName(doctor.getDepartment().getName());
        response.setDegree(doctor.getDegree());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setConsultationHours(doctor.getConsultationHours());
        response.setRating(doctor.getRating());
        response.setAbout(doctor.getAbout());
        response.setImageUrl(doctor.getImageUrl());
        return response;
    }
}
