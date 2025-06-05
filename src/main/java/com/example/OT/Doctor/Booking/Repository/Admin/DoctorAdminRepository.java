package com.example.OT.Doctor.Booking.Repository.Admin;

import com.example.OT.Doctor.Booking.Entity.Department;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorAdminRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDepartment(Department department);
    @Query("SELECT d FROM Doctor d WHERE d.user.id = :userId")
    Optional<Doctor> findByUserId(@Param("userId") Long userId);
}
