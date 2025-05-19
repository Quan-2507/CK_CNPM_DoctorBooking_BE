package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionResponseDTO {
    private Long id;
    private String patientName;
    private Integer patientAge;
    private User.Gender patientGender;
    private String diseaseNameVi;
    private String medicineName;
    private String unit;
    private Integer quantity;
    private String dosage;
    private String duration;
    private String note;
    private String prescriptionDate;
}