package com.example.OT.Doctor.Booking.DTO;

public class DoctorDTO {
    private Long id; // Thêm field id
    private String name;
    private int experienceYears;
    private String imageUrl;

    // Constructor có id
    public DoctorDTO(Long id, String name, int experienceYears, String imageUrl) {
        this.id = id;
        this.name = name;
        this.experienceYears = experienceYears;
        this.imageUrl = imageUrl;
    }

    // Getter & Setter cho id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter & Setter cho các field còn lại
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
