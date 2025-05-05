package com.example.OT.Doctor.Booking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diseases")
@Getter
@Setter
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_vi", nullable = false, length = 100)
    private String nameVi;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "name_en", length = 100)
    private String nameEn;
}
