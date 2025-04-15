package com.example.OT.Doctor.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String email;

    public LoginResponse(String jwt, String email) {
        this.token = jwt;
        this.email = email;
    }
}