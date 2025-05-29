package com.example.OT.Doctor.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String email;
    private String phoneNumber;
    private String username;
    private String address;
    private String gender;

    // Constructor
    public UserInfoDTO(String email, String phoneNumber, String username, String address, String gender) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.gender = gender;
        this.address = address;
    }
}
