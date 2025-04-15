package com.example.OT.Doctor.Booking.DTO;

public class DoctorDetailDTO {
    private Long id;
    private String name;
    private int experienceYears;
    private String degree;
    private int consultationHours;
    private String imageUrl;
    private String departmentName;

    // Constructor
    public DoctorDetailDTO(Long id, String name, int experienceYears, String degree, int consultationHours, String imageUrl, String departmentName) {
        this.id = id;
        this.name = name;
        this.experienceYears = experienceYears;
        this.degree = degree;
        this.consultationHours = consultationHours;
        this.imageUrl = imageUrl;
        this.departmentName = departmentName;
    }

    // Getter và Setter
    // (Thêm getter và setter cho tất cả các trường)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public int getConsultationHours() { return consultationHours; }
    public void setConsultationHours(int consultationHours) { this.consultationHours = consultationHours; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}