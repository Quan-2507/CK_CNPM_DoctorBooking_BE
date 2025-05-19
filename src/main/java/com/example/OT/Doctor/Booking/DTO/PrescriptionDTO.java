package com.example.OT.Doctor.Booking.DTO;

import java.util.List;

public class PrescriptionDTO {
    private String doctorName;
    private String patientName;
    private Double totalCost;
    private String note;
    private List<PrescriptionDetailDTO> medicines;


    public PrescriptionDTO(String doctorName, String patientName, Double totalCost, String note, List<PrescriptionDetailDTO> medicines) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.totalCost = totalCost;
        this.note = note;
        this.medicines = medicines;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<PrgescriptionDetailDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<PrescriptionDetailDTO> medicines) {
        this.medicines = medicines;
    }
}
