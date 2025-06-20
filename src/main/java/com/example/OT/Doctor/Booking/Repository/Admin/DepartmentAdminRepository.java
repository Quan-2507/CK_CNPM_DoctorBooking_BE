package com.example.OT.Doctor.Booking.Repository.Admin;

import com.example.OT.Doctor.Booking.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentAdminRepository extends JpaRepository<Department, Long> {
    boolean existsByNameEn(String nameEn);
    boolean existsByNameVi(String nameVi);
    List<Department> findByIsActive(Integer isActive);
}