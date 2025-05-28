package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByOrderByExperienceYearsDesc(); // Thêm phương thức này
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> searchByName(@Param("name") String name);
    // Tìm bác sĩ theo departmentId và sắp xếp theo số năm kinh nghiệm giảm dần
    @Query("SELECT d FROM Doctor d WHERE d.department.id = :departmentId ORDER BY d.experienceYears DESC")
    List<Doctor> findByDepartmentIdOrderByExperienceYearsDesc(@Param("departmentId") Long departmentId);

    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.department WHERE d.id = :id")
    Optional<Doctor> findByIdWithDepartment(@Param("id") Long id);

}