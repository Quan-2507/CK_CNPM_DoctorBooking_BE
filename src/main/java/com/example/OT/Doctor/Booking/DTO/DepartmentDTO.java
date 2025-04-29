package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.Department;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DepartmentDTO {
    private final String departmentName;
    private final List<SymptomDTO> symptoms;

    public DepartmentDTO(Department department) {
        this.departmentName = department.getName();
        this.symptoms = department.getSymptoms().stream()
                .map(SymptomDTO::new)
                .collect(Collectors.toList());
    }
}