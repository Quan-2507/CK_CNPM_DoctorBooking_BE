package com.example.OT.Doctor.Booking.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Doctor Booking API",
                version = "1.0",
                description = "API for booking doctor appointments"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Profile("dev")
//@Schema(
//        description = "QR code base64 và secret key",
//        example = """
//    {
//        "qrCode": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
//        "secretKey": "LJVHT7YUBK5AYNXVMPJZWFTCRBZKOO4H"
//    }"""
//)

public class SwaggerConfig {
    // Có thể thêm các tùy chỉnh khác nếu cần
}
