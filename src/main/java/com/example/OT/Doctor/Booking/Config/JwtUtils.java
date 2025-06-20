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

    public String generateJwtToken(User userDetails) {
        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getId())) // Sử dụng userId làm subject
                .claim("role", "ROLE_" + userDetails.getRole().name())
                .claim("username", userDetails.getUsername()) // Lưu username như một claim bổ sung
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            if (token == null || token.split("\\.").length != 3) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        }
        return false;
    }

    public Long getUserIdFromJwtToken(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject());
    }

    public String getClaimFromJwtToken(String token, String claimName) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get(claimName, String.class);
    }
}