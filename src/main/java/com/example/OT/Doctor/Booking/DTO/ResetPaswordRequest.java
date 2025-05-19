package com.example.OT.Doctor.Booking.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResetPaswordRequest {
    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    private String currentPassword;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String newPassword;
    @NotBlank(message = "Mật khẩu mới không khớp")
    private String confirmPassword;
}
