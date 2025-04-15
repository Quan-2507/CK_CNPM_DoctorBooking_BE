package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
//import com.example.OT.Doctor.Booking.DTO.LoginRequest;
import com.example.OT.Doctor.Booking.DTO.LoginRequest;
import com.example.OT.Doctor.Booking.DTO.LoginResponse;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Enum.Role;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import com.example.OT.Doctor.Booking.Service.AuthService;
import com.example.OT.Doctor.Booking.DTO.SignUpDTO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    @Operation(summary = "Register a new user", responses = {@ApiResponse(responseCode = "200", description = "User registered successfully!"), @ApiResponse(responseCode = "400", description = "Invalid input")}
//            ,security = { @SecurityRequirement(name = "bearerAuth") }
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Login request received: " + loginRequest.getEmail()); // 🔍 Debug
            LoginResponse response = authService.login(loginRequest);
            System.out.println("Login successful, token: " + response.getToken()); // 🔍 Debug
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage()); // 🔍 Debug
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @GetMapping("/oauth2/success")
//    public ResponseEntity<?> oauth2Success(@AuthenticationPrincipal OAuth2User oauth2User) {
//        String email = oauth2User.getAttribute("email");
//        String name = oauth2User.getAttribute("name");
//
//        // Kiểm tra user đã tồn tại chưa, nếu chưa thì tạo mới
//        User user = userRepository.findByEmail(email).orElseGet(() -> {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setUsername(name);
//            newUser.setRole(Role.ROLE_PATIENT);
//            return userRepository.save(newUser);
//        });
//
//        // Tạo JWT token
//        String jwt = jwtUtils.generateJwtToken(user);
//        return ResponseEntity.ok(Map.of("token", jwt));
//    }

    //    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
//        return authService.login(request);
//    }
//    @Operation(summary = "Lấy QR code cho 2FA")
//    @GetMapping("/2fa/qrcode")
//    public ResponseEntity<Map<String, String>> generateQRCode(@RequestParam String username, @RequestParam String secretKey) throws WriterException, IOException, java.io.IOException {
//
//        String qrContent = String.format("otpauth://totp/%s?secret=%s&issuer=MyApp", URLEncoder.encode(username, StandardCharsets.UTF_8), secretKey);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
//
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
//        String base64QR = Base64.getEncoder().encodeToString(outputStream.toByteArray());
//
//        return ResponseEntity.ok(Map.of("qrCode", "data:image/png;base64," + base64QR, "secretKey", secretKey));
//    }
}