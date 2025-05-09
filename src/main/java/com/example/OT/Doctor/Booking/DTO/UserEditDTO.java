package com.example.OT.Doctor.Booking.DTO;

import com.example.OT.Doctor.Booking.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {
    private String email;
    private String phoneNumber;
    private String username;
    private User.Gender gender;

    // Constructor
    public UserEditDTO(String email, String phoneNumber, String username, User.Gender gender) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.gender = gender;
    }
}
