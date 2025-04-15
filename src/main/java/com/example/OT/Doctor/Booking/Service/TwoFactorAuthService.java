package com.example.OT.Doctor.Booking.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthService {
    private final GoogleAuthenticator gAuth;

    public TwoFactorAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    // Tạo secret key mới cho user
    public String generateNewSecret() {
        return gAuth.createCredentials().getKey();
    }

    // Tạo QR Code URL để user quét bằng Google Authenticator
    public String getQRCodeURL(String username, String secret) {
        return "otpauth://totp/" + username + "?secret=" + secret + "&issuer=MyApp";
    }

    // Xác minh mã OTP
    public boolean verifyOTP(String secret, int otp) {
        return gAuth.authorize(secret, otp);
    }
}