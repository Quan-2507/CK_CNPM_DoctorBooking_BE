package com.example.OT.Doctor.Booking.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_vi", nullable = true)
    private String nameVi;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Doctor> doctors = new HashSet<>();

}
