package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.DoctorDTO;
import com.example.OT.Doctor.Booking.DTO.DoctorDetailDTO;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAllByOrderByExperienceYearsDesc(); // Lấy danh sách theo thứ tự giảm dần
    }

    public List<Doctor> getDoctorsByName(String name) {
        return doctorRepository.searchByName(name);
    }

    public List<DoctorDTO> getDoctorsByDepartmentId(Long departmentId) {
        List<Doctor> doctors = doctorRepository.findByDepartmentIdOrderByExperienceYearsDesc(departmentId);
        return doctors.stream()
                .map(doctor -> new DoctorDTO(
                        doctor.getName(),
                        doctor.getExperienceYears(),
                        doctor.getImageUrl()))
                .collect(Collectors.toList());
    }
    public DoctorDetailDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findByIdWithDepartment(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        String departmentName = (doctor.getDepartment() != null) ? doctor.getDepartment().getNameEn() : "Unknown";

        return new DoctorDetailDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getExperienceYears(),
                doctor.getDegree(),
                doctor.getConsultationHours(),
                doctor.getImageUrl(),
                departmentName
        );
    }
}