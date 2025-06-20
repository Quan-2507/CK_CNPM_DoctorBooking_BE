package com.example.OT.Doctor.Booking.Config;

import com.example.OT.Doctor.Booking.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Tạo JWT với subject là userId
    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId())) // Subject là userId
                .claim("username", user.getUsername())    // Optional: để hiển thị
                .claim("role", "ROLE_" + user.getRole().name()) // Vai trò
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Validate JWT
    public boolean validateJwtToken(String token) {
        try {
            if (token == null || token.split("\\.").length != 3) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }

    // Trích xuất userId từ token (subject)
    public Long getUserIdFromJwtToken(String token) {
        String subject = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(subject);
    }

    // Trích xuất claim bất kỳ (role, username,...)
    public String getClaimFromJwtToken(String token, String claimName) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, String.class);
    }
}
