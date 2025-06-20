package com.example.OT.Doctor.Booking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "symptoms")
@Getter
@Setter
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_vi", nullable = false)
    private String nameVi;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition = "INTEGER DEFAULT 1")
    private Integer isActive;
}