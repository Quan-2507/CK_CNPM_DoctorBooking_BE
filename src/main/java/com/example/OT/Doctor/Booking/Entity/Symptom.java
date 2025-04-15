package com.example.OT.Doctor.Booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "symptoms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "symptom_department",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
    private Set<Department> departments;

    @Override
    public String toString() {
        return "Symptom{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
}
