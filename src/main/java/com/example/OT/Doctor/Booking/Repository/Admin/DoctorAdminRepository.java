package com.example.OT.Doctor.Booking.Repository.Admin;

import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorAdminRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDepartmentAndIsActive(Department department, Integer isActive);

    @Query("SELECT d FROM Doctor d WHERE d.user.id = :userId AND d.isActive = 1")
    Optional<Doctor> findByUserId(@Param("userId") Long userId);
}