package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.PrescriptionRequestDTO;
import com.example.OT.Doctor.Booking.DTO.PrescriptionResponseDTO;
import com.example.OT.Doctor.Booking.Entity.Appointment;
import com.example.OT.Doctor.Booking.Entity.Disease;
import com.example.OT.Doctor.Booking.Entity.Doctor;
import com.example.OT.Doctor.Booking.Entity.Medicine;
import com.example.OT.Doctor.Booking.Entity.Prescription;
import com.example.OT.Doctor.Booking.Entity.PrescriptionDetail;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Repository.AppointmentRepository;
import com.example.OT.Doctor.Booking.Repository.DiseaseRepository;
import com.example.OT.Doctor.Booking.Repository.MedicineRepository;
import com.example.OT.Doctor.Booking.Repository.PrescriptionDetailRepository;
import com.example.OT.Doctor.Booking.Repository.PrescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PrescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionService.class);

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Transactional
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request) {
        // Lấy username từ SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Processing prescription request for user: {}", username);
        logger.info("SecurityContext authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        // Tìm lịch hẹn
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Lịch hẹn không tồn tại"));

        // Kiểm tra bác sĩ phụ trách lịch hẹn
        Doctor doctor = appointment.getDoctor();
        if (doctor == null) {
            logger.error("Doctor not found for appointment ID: {}", request.getAppointmentId());
            throw new IllegalArgumentException("Không tìm thấy thông tin bác sĩ cho lịch hẹn này");
        }

        // Lấy User từ Doctor để kiểm tra username
        User doctorUser = doctor.getUser();
        if (doctorUser == null) {
            logger.error("User not found for doctor ID: {}", doctor.getId());
            throw new IllegalArgumentException("Không tìm thấy thông tin user của bác sĩ");
        }

        logger.info("Doctor username: {}", doctorUser.getUsername());
        if (!doctorUser.getUsername().equals(username)) {
            logger.warn("User {} is not authorized to create prescription for appointment ID: {}", username, request.getAppointmentId());
            throw new IllegalArgumentException("Bạn không có quyền kê đơn cho lịch hẹn này");
        }

        // Tìm thuốc
        Medicine medicine = medicineRepository.findById(request.getMedicineId())
                .orElseThrow(() -> new IllegalArgumentException("Thuốc không tồn tại"));

        // Kiểm tra số lượng tồn kho
        if (medicine.getStockQuantity() == null || medicine.getStockQuantity() < request.getQuantity()) {
            logger.error("Insufficient stock for medicine: {}, requested: {}, available: {}", medicine.getName(), request.getQuantity(), medicine.getStockQuantity());
            throw new IllegalArgumentException("Không đủ tồn kho cho thuốc: " + medicine.getName());
        }

        // Tìm bệnh
        Disease disease = diseaseRepository.findById(request.getDiseaseId())
                .orElseThrow(() -> new IllegalArgumentException("Bệnh không tồn tại"));

        // Lấy thông tin bệnh nhân từ Appointment
        User patient = appointment.getUser();
        if (patient == null) {
            logger.error("Patient not found for appointment ID: {}", request.getAppointmentId());
            throw new IllegalArgumentException("Không tìm thấy thông tin bệnh nhân cho lịch hẹn này");
        }

        // Kiểm tra fullName bắt buộc
        if (patient.getFullName() == null || patient.getFullName().trim().isEmpty()) {
            logger.error("Patient full name is missing for user ID: {}", patient.getId());
            throw new IllegalArgumentException("Tên đầy đủ của bệnh nhân là bắt buộc");
        }

        // Tính tuổi bệnh nhân
        Integer patientAge = null;
        if (patient.getDateOfBirth() != null) {
            patientAge = LocalDate.now().getYear() - patient.getDateOfBirth().getYear();
        } else {
            logger.error("Patient date of birth is missing for user ID: {}", patient.getId());
            throw new IllegalArgumentException("Ngày sinh của bệnh nhân là bắt buộc");
        }

        // Tạo đơn thuốc
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setNote(request.getNote());
        prescription.setTotalCost(medicine.getPrice() != null ? medicine.getPrice() * request.getQuantity() : 0.0);
        prescription.setStatus("ACTIVE");

        // Lưu đơn thuốc
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        logger.info("Prescription saved with ID: {}", savedPrescription.getId());

        // Tạo chi tiết đơn thuốc
        PrescriptionDetail detail = new PrescriptionDetail();
        detail.setPrescription(savedPrescription);
        detail.setMedicine(medicine);
        detail.setQuantity(request.getQuantity());
        detail.setDosage(request.getDosage());
        detail.setDuration(request.getDuration());
        detail.setNote(request.getNote());

        // Lưu chi tiết đơn thuốc
        prescriptionDetailRepository.save(detail);
        logger.info("Prescription detail saved for prescription ID: {}", savedPrescription.getId());

        // Cập nhật số lượng tồn kho
        medicine.setStockQuantity(medicine.getStockQuantity() - request.getQuantity());
        medicineRepository.save(medicine);

        // Định dạng prescriptionDate
        String formattedDate = null;
        if (appointment.getAppointmentDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:HH dd/MM/yyyy");
            formattedDate = appointment.getAppointmentDate().format(formatter);
        } else {
            logger.warn("Appointment date is null for appointment ID: {}", request.getAppointmentId());
        }

        // Tạo response
        PrescriptionResponseDTO response = new PrescriptionResponseDTO();
        response.setId(savedPrescription.getId());
        response.setPatientName(patient.getFullName());
        response.setPatientAge(patientAge);
        response.setPatientGender(patient.getGender());
        response.setDiseaseNameVi(disease.getNameVi());
        response.setMedicineName(medicine.getName());
        response.setUnit(medicine.getUnit());
        response.setQuantity(request.getQuantity());
        response.setDosage(request.getDosage());
        response.setDuration(request.getDuration());
        response.setNote(request.getNote());
        response.setPrescriptionDate(formattedDate);

        logger.info("Prescription created successfully for patient: {}", patient.getFullName());
        return response;
    }
}