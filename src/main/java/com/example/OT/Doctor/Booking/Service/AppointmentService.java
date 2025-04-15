package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.AppointmentResponse;
import com.example.OT.Doctor.Booking.DTO.BookAppointmentRequest;
import com.example.OT.Doctor.Booking.Entity.Appointment;
import com.example.OT.Doctor.Booking.Exception.BookingConflictException;
import com.example.OT.Doctor.Booking.Repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // Đánh dấu lớp này là một service trong Spring, giúp quản lý logic nghiệp vụ.
@RequiredArgsConstructor // Tự động tạo constructor với các dependency được đánh dấu là final.
public class AppointmentService {
    private final AppointmentRepository appointmentRepository; // Repository dùng để thao tác với cơ sở dữ liệu.

    /**
     * Đặt lịch hẹn mới.
     * @param request Thông tin đặt lịch hẹn.
     * @return AppointmentResponse Đối tượng phản hồi chứa thông tin lịch hẹn đã đặt.
     */
     // Đảm bảo tính toàn vẹn dữ liệu: nếu có lỗi, toàn bộ thao tác sẽ bị rollback.
    public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
        // Kiểm tra xem bác sĩ đã có lịch hẹn vào thời gian này chưa
        if (appointmentRepository.existsByDoctorIdAndAppointmentDate(
                request.getDoctorId(), request.getAppointmentDate())) {
            throw new BookingConflictException("This time slot is already booked"); // Nếu đã có lịch, ném ngoại lệ.
        }

        // Tạo một đối tượng lịch hẹn mới với trạng thái "CONFIRMED"
        Appointment appointment = Appointment.builder()
                .userId(request.getUserId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .status("CONFIRMED") // Mặc định trạng thái là "CONFIRMED".
                .build();

        // Lưu lịch hẹn vào database
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Chuyển đổi đối tượng lịch hẹn sang dạng phản hồi và trả về kết quả.
        return convertToResponse(savedAppointment);
    }

    /**
     * Lấy danh sách lịch hẹn của người dùng.
     * @param userId ID của người dùng.
     * @return Danh sách lịch hẹn đã đặt theo thứ tự giảm dần của ngày hẹn.
     */
    public List<AppointmentResponse> getUserAppointments(Integer userId) {
        return appointmentRepository.findByUserIdOrderByAppointmentDateDesc(userId)
                .stream()
                .map(this::convertToResponse) // Chuyển đổi từng lịch hẹn thành DTO phản hồi.
                .collect(Collectors.toList()); // Thu thập kết quả vào danh sách.
    }

    /**
     * Chuyển đổi từ đối tượng lịch hẹn (Entity) sang phản hồi (DTO).
     * @param appointment Đối tượng lịch hẹn.
     * @return Đối tượng phản hồi AppointmentResponse.
     */
    private AppointmentResponse convertToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .userId(appointment.getUserId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt()) // Lấy thông tin ngày tạo lịch hẹn.
                .build();
    }
}
