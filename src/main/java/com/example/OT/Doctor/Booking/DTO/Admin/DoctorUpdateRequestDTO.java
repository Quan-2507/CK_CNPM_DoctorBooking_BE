package com.example.OT.Doctor.Booking.DTO.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorUpdateRequestDTO {
    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    private String name;

    private Long departmentId;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có 10 chữ số")
    private String phoneNumber;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Min(value = 0, message = "Số năm kinh nghiệm không được âm")
    private Integer experienceYears;

    @Size(max = 255, message = "Bằng cấp không được vượt quá 255 ký tự")
    private String degree;

    @Min(value = 0, message = "Giờ tư vấn không được âm")
    private Integer consultationHours;

    @Min(value = 0, message = "Điểm đánh giá không được âm")
    private Float rating;

    @Size(max = 1000, message = "Thông tin giới thiệu không được vượt quá 1000 ký tự")
    private String about;

    @Size(max = 255, message = "URL ảnh không được vượt quá 255 ký tự")
    private String imageUrl;

    private Long userId;
}