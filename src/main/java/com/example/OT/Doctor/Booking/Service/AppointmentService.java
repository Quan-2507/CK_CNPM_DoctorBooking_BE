package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.AppointmentResponse;
import com.example.OT.Doctor.Booking.DTO.BookAppointmentRequest;
import com.example.OT.Doctor.Booking.Entity.Appointment;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Entity.Schedule;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Exception.BookingConflictException;
import com.example.OT.Doctor.Booking.Repository.AppointmentRepository;
import com.example.OT.Doctor.Booking.Repository.DoctorRepository;
import com.example.OT.Doctor.Booking.Repository.ScheduleRepository;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
        // Kiểm tra user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Kiểm tra bác sĩ
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Kiểm tra lịch
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        // Kiểm tra trùng lịch
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(doctor.getId(), request.getAppointmentTime())) {
            throw new BookingConflictException("This time slot is already booked");
        }

        // Kiểm tra số ghế còn lại
        if (schedule.getRemainingSeats() <= 0) {
            throw new IllegalArgumentException("No available seats for this schedule");
        }

        // Tính số thứ tự
        List<Appointment> existingAppointments = appointmentRepository.findByScheduleId(schedule.getId());
        int appointmentNumber = existingAppointments.size() + 1;

        // Tạo lịch hẹn
        Appointment appointment = Appointment.builder()
                .user(user)
                .doctor(doctor)
                .schedule(schedule)
                .appointmentTime(request.getAppointmentTime())
                .symptomDescription(request.getSymptomDescription())
                .status(Appointment.Status.SCHEDULED)
                .paymentStatus(Appointment.PaymentStatus.PENDING)
                .appointmentNumber(appointmentNumber)
                .build();

        // Giảm số ghế còn lại
        schedule.setRemainingSeats(schedule.getRemainingSeats() - 1);
        scheduleRepository.save(schedule);

        // Lưu lịch hẹn
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Trả về response
        return AppointmentResponse.builder()
                .id(savedAppointment.getId())
                .userId(savedAppointment.getUser().getId())
                .doctorId(savedAppointment.getDoctor().getId())
                .doctorName(savedAppointment.getDoctor().getName())
                .appointmentTime(savedAppointment.getAppointmentTime())
                .appointmentNumber(savedAppointment.getAppointmentNumber())
                .status(savedAppointment.getStatus().name())
                .createdAt(savedAppointment.getCreatedAt())
                .build();
    }

    public List<AppointmentResponse> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserIdOrderByAppointmentTimeDesc(userId)
                .stream()
                .map(appointment -> AppointmentResponse.builder()
                        .id(appointment.getId())
                        .userId(appointment.getUser().getId())
                        .doctorId(appointment.getDoctor().getId())
                        .doctorName(appointment.getDoctor().getName())
                        .appointmentTime(appointment.getAppointmentTime())
                        .appointmentNumber(appointment.getAppointmentNumber())
                        .status(appointment.getStatus().name())
                        .createdAt(appointment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}