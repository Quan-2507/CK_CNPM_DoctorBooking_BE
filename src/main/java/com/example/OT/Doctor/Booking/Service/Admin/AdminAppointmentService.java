package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.AppointmentResponseDTO;
import com.example.OT.Doctor.Booking.Entity.Appointment;
import com.example.OT.Doctor.Booking.Repository.Admin.AppointmentAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AdminAppointmentService.class);

    @Autowired
    private AppointmentAdminRepository appointmentRepository;

    public List<AppointmentResponseDTO> getAppointments(Long doctorId, LocalDate date) {
        logger.info("Fetching appointments with doctorId: {} and date: {}", doctorId, date);

        List<Appointment> appointments;
        if (doctorId != null && date != null) {
            appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);
        } else if (doctorId != null) {
            appointments = appointmentRepository.findByDoctorId(doctorId);
        } else if (date != null) {
            appointments = appointmentRepository.findByDate(date);
        } else {
            appointments = appointmentRepository.findAllAppointments();
        }

        List<AppointmentResponseDTO> response = appointments.stream()
                .map(this::mapToAppointmentResponseDTO)
                .collect(Collectors.toList());

        logger.info("Fetched {} appointments", response.size());
        return response;
    }

    private AppointmentResponseDTO mapToAppointmentResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setScheduleDate(appointment.getSchedule() != null ? appointment.getSchedule().getDate() : null);
        dto.setStartTime(appointment.getSchedule() != null ? appointment.getSchedule().getStartTime() : null);
        dto.setEndTime(appointment.getSchedule() != null ? appointment.getSchedule().getEndTime() : null);
        dto.setDoctorName(appointment.getDoctor() != null ? appointment.getDoctor().getName() : null);
        dto.setAppointmentNumber(appointment.getAppointmentNumber());
        dto.setUserName(appointment.getUser() != null ? appointment.getUser().getUsername() : null);
        return dto;
    }
}