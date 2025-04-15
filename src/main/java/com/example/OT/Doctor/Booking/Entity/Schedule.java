package com.example.OT.Doctor.Booking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "schedule", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "remaining_seats", nullable = false)
    private Integer remainingSeats;

    @Column(name = "num_of_seats", nullable = false)
    private Integer numOfSeats;
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}