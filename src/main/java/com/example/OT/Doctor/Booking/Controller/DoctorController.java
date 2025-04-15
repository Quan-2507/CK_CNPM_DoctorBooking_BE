package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.DoctorDTO;
import com.example.OT.Doctor.Booking.DTO.DoctorDetailDTO;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

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


