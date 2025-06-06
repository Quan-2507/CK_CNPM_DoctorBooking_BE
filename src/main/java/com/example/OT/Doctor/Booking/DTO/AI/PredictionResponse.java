package com.example.OT.Doctor.Booking.DTO.AI;

public class PredictionResponse {
    private String predicted_disease;
    private String predicted_specialty;

    public String getPredicted_disease() { return predicted_disease; }
    public void setPredicted_disease(String d) { this.predicted_disease = d; }

    public String getPredicted_specialty() { return predicted_specialty; }
    public void setPredicted_specialty(String s) { this.predicted_specialty = s; }
}
