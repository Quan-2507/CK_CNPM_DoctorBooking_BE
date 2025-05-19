package com.example.OT.Doctor.Booking.DTO;

public class PrescriptionDetailDTO {
    private Long medicineId;
    private String medicineName;  // Lấy tên thuốc từ bảng Medicines
    private String dosage;
    private String duration;
    private String note;

    // Constructor
    public PrescriptionDetailDTO(Long medicineId, String medicineName, String dosage, String duration, String note) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.duration = duration;
        this.note = note;
    }

    // getters và setters
    // ...
}

