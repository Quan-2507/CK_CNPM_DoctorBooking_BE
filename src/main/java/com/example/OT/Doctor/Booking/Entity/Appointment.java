package com.example.OT.Doctor.Booking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "appointments", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Giữ nguyên Integer vì database dùng integer

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;

    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}