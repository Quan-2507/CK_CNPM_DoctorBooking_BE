package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.DepartmentDTO;
import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<DepartmentDTO> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(DepartmentDTO::new)
                .collect(Collectors.toList());
    }
}