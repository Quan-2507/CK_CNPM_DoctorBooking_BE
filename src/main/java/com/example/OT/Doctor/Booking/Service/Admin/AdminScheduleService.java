package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.ScheduleRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.ScheduleResponseDTO;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Entity.Schedule;
import com.example.OT.Doctor.Booking.Repository.DoctorRepository;
import com.example.OT.Doctor.Booking.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AdminScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO request) {
        Doctor doctor = doctorRepository.findByIdWithDepartment(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + request.getDoctorId()));

        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        try {
            date = LocalDate.parse(request.getDate());
            startTime = LocalTime.parse(request.getStartTime());
            endTime = LocalTime.parse(request.getEndTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date/time format");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setNumOfSeats(request.getNumOfSeats());
        schedule.setRemainingSeats(request.getNumOfSeats());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDTO responseDTO = new ScheduleResponseDTO();
        responseDTO.setId(savedSchedule.getId());
        responseDTO.setDoctorId(doctor.getId());
        responseDTO.setDoctorName(doctor.getName());
        responseDTO.setDepartmentName(doctor.getDepartment().getNameEn());
        responseDTO.setDate(savedSchedule.getDate().toString());
        responseDTO.setStartTime(savedSchedule.getStartTime().toString());
        responseDTO.setEndTime(savedSchedule.getEndTime().toString());
        responseDTO.setRemainingSeats(savedSchedule.getRemainingSeats());
        responseDTO.setNumOfSeats(savedSchedule.getNumOfSeats());

        return responseDTO;
    }

}
