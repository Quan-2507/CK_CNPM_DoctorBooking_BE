package com.example.OT.Doctor.Booking.Controller.Admin;

import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
@CrossOrigin(origins = "*")
public class AdminDepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
