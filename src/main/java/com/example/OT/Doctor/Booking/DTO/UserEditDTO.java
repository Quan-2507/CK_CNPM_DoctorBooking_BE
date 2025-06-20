package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {
    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có 10 chữ số")
    private String phoneNumber;

    private String username;

    private String address;

    private User.Gender gender;

    public UserEditDTO(String email, String phoneNumber, String username, String address, User.Gender gender) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.address = address;
        this.gender = gender;
    }
}