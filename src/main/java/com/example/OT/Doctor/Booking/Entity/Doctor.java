package com.example.OT.Doctor.Booking.Entity;

import com.example.OT.Doctor.Booking.Entity.Department;
import jakarta.persistence.*;
@Entity
@Table(name = "Doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int experienceYears;
    private String degree;
    private int consultationHours;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)  // Liên kết với Department
    private Department department;

    // Getter và Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getConsultationHours() {
        return consultationHours;
    }

    public void setConsultationHours(int consultationHours) {
        this.consultationHours = consultationHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}