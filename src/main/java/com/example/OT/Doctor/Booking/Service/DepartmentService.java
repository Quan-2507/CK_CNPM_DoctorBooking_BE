package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
}
