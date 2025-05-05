package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
import com.example.OT.Doctor.Booking.DTO.LoginRequest;
import com.example.OT.Doctor.Booking.DTO.LoginResponse;
import com.example.OT.Doctor.Booking.Service.AuthService;
import com.example.OT.Doctor.Booking.DTO.SignUpDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authService.sendVerificationCode(email);
        return ResponseEntity.ok("Verification code sent to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String verifyCode, @RequestParam String newPassword) {
        authService.resetPassword(email, verifyCode, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}