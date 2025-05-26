package com.example.OT.Doctor.Booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionRequestDTO {
    @NotNull(message = "ID lịch hẹn không được để trống")
    private Long appointmentId;

    @NotNull(message = "ID bệnh không được để trống")
    private Long diseaseId;

    @NotNull(message = "Danh sách thuốc không được để trống")
    private List<MedicineDetail> medicines;

    @Getter
    @Setter
    public static class MedicineDetail {
        @NotNull(message = "ID thuốc không được để trống")
        private Long medicineId;

        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        private Integer quantity;

        private String dosage; // Liều dùng (ví dụ: 2 viên/ngày)

        private String duration; // Thời gian sử dụng (ví dụ: 5 ngày)

        private String note; // Ghi chú
    }
}