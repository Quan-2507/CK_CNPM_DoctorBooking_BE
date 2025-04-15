package com.example.OT.Doctor.Booking.DTO;

public class DoctorDTO {
    private String name;
    private int experienceYears;
    private String imageUrl;

    // Constructor
    public DoctorDTO(String name, int experienceYears, String imageUrl) {
        this.name = name;
        this.experienceYears = experienceYears;
        this.imageUrl = imageUrl;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}