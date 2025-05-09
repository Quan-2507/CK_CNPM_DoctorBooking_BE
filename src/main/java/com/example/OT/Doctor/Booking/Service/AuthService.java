package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
import com.example.OT.Doctor.Booking.DTO.LoginRequest;
import com.example.OT.Doctor.Booking.DTO.LoginResponse;
import com.example.OT.Doctor.Booking.DTO.SignUpDTO;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Enum.Role;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(SignUpDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username đã tồn tại!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.PATIENT);
        user.setStatus(User.Status.ACTIVE);

        userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email không tồn tại");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu không chính xác");
        }

        String token = jwtUtils.generateJwtToken(user);
        return new LoginResponse(token, user.getEmail());
    }

    public void sendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String verifyCode = UUID.randomUUID().toString().substring(0, 6);
        user.setVerifyCode(verifyCode);
        user.setVerifyCodeExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        // Gửi email với verifyCode (giả lập, cần tích hợp dịch vụ email thực tế)
        System.out.println("Verification code for " + email + ": " + verifyCode);
    }

    public void resetPassword(String email, String verifyCode, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!verifyCode.equals(user.getVerifyCode()) || user.getVerifyCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired verification code");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerifyCode(null);
        user.setVerifyCodeExpiry(null);
        userRepository.save(user);
    }
}