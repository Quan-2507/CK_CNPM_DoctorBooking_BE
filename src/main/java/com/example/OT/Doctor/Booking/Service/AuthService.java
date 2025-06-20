package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
import com.example.OT.Doctor.Booking.DTO.LoginRequest;
import com.example.OT.Doctor.Booking.DTO.LoginResponse;
import com.example.OT.Doctor.Booking.DTO.ResetPaswordRequest;
import com.example.OT.Doctor.Booking.DTO.SignUpDTO;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Enum.Role;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername()); // Username có thể trùng
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.PATIENT);
        user.setStatus(User.Status.ACTIVE);

        userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại"));

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

        System.out.println("Verification code for " + email + ": " + verifyCode);
    }

    public void resetPassword(ResetPaswordRequest resetPaswordRequest) {
        // Lấy userId từ SecurityContext
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Long userId = Long.valueOf(userIdStr);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(resetPaswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng");
        }

        if (!resetPaswordRequest.getNewPassword().equals(resetPaswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Xác nhận mật khẩu không khớp");
        }

        if (passwordEncoder.matches(resetPaswordRequest.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu hiện tại");
        }

        user.setPassword(passwordEncoder.encode(resetPaswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}