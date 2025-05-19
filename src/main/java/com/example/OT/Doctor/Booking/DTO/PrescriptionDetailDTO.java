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

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

