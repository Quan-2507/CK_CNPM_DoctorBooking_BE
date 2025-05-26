package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionResponseDTO {
    private Long id;
    private String patientName;
    private Integer patientAge;
    private User.Gender patientGender;
    private String diseaseNameVi;
    private List<MedicineDetail> medicines;
    private String prescriptionDate;

    @Getter
    @Setter
    public static class MedicineDetail {
        private String medicineName;
        private String unit;
        private Integer quantity;
        private String dosage;
        private String duration;
        private String note;
    }
}