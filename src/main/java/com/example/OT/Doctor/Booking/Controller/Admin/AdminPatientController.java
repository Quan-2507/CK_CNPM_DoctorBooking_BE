package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Enum.Role;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/patients")
@CrossOrigin(origins = "*") // Cho phép React frontend gọi
public class AdminPatientController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllPatients() {
        return userRepository.findByRole(Role.PATIENT);
    }
}