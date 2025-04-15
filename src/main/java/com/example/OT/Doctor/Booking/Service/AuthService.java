package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
//import com.example.OT.Doctor.Booking.DTO.LoginRequest;
//import com.example.OT.Doctor.Booking.DTO.LoginResponse;
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
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

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
        user.setRole(Role.ROLE_PATIENT);

        // Bật 2FA mặc định
        String secret2FA = twoFactorAuthService.generateNewSecret();
        user.setSecret2FA(secret2FA);

        userRepository.save(user);
        // Trả về QR Code URL để frontend hiển thị
        String qrCodeURL = twoFactorAuthService.getQRCodeURL(user.getUsername(), secret2FA);
        return ResponseEntity.ok(Map.of(
                "message", "Đăng ký thành công! Quét QR để bật 2FA",
                "qrCodeURL", qrCodeURL
        ));
//        return ResponseEntity.ok("Đăng ký thành công!");
    }
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email không tồn tại");
        }

        User user = userOptional.get();

        // Kiểm tra nếu password đã được mã hóa hay chưa
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu không chính xác");
        }

        // Tạo JWT token
        String token = jwtUtils.generateJwtToken(user);

        return new LoginResponse(token, user.getEmail());
    }

}
