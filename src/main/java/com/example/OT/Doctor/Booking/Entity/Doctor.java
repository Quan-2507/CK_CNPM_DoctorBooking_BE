package com.example.OT.Doctor.Booking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "degree")
    private String degree;

    @Column(name = "consultation_hours")
    private Integer consultationHours;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "about")
    private String about;

    @Column(name = "image_url")
    private String imageUrl;
}