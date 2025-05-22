package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.PrescriptionDTO;
import com.example.OT.Doctor.Booking.DTO.PrescriptionDetailDTO;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        logger.info("Request details: appointmentId={}, diseaseId={}, medicines={}",
                request.getAppointmentId(), request.getDiseaseId(), request.getMedicines());

        // Kiểm tra dữ liệu đầu vào
        if (request.getAppointmentId() == null || request.getDiseaseId() == null) {
            logger.error("Invalid request data: appointmentId or diseaseId is null");
            throw new IllegalArgumentException("Dữ liệu không hợp lệ: ID lịch hẹn hoặc bệnh không được để trống");
        }
        if (request.getMedicines() == null || request.getMedicines().isEmpty()) {
            logger.error("Invalid request data: medicines list is null or empty");
            throw new IllegalArgumentException("Dữ liệu không hợp lệ: Danh sách thuốc không được để trống");
        }

        // Kiểm tra từng thuốc trong danh sách
        for (PrescriptionRequestDTO.MedicineDetail med : request.getMedicines()) {
            if (med.getMedicineId() == null) {
                logger.error("Invalid medicine data: medicineId is null");
                throw new IllegalArgumentException("Dữ liệu không hợp lệ: ID thuốc không được để trống");
            }
            if (med.getQuantity() == null || med.getQuantity() <= 0) {
                logger.error("Invalid medicine data: quantity is invalid for medicineId={}", med.getMedicineId());
                throw new IllegalArgumentException("Dữ liệu không hợp lệ: Số lượng thuốc phải lớn hơn 0 cho thuốc ID: " + med.getMedicineId());
            }
        }

        // Tìm lịch hẹn
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID: {}", request.getAppointmentId());
                    return new IllegalArgumentException("Lịch hẹn không tồn tại với ID: " + request.getAppointmentId());
                });
        logger.info("Found appointment with ID: {}, user_id: {}, doctor_id: {}, disease_id: {}, schedule_id: {}, status: {}, appointment_date: {}",
                appointment.getId(),
                appointment.getUser() != null ? appointment.getUser().getId() : null,
                appointment.getDoctor() != null ? appointment.getDoctor().getId() : null,
                appointment.getDisease() != null ? appointment.getDisease().getId() : null,
                appointment.getSchedule() != null ? appointment.getSchedule().getId() : null,
                appointment.getStatus(),
                appointment.getAppointmentDate());

        // Kiểm tra trạng thái lịch hẹn
        if (appointment.getStatus() != Appointment.Status.SCHEDULED) {
            logger.error("Appointment ID: {} is not in SCHEDULED status, current status: {}", request.getAppointmentId(), appointment.getStatus());
            throw new IllegalArgumentException("Chỉ có thể kê đơn cho lịch hẹn ở trạng thái SCHEDULED");
        }

        // Kiểm tra schedule_id nếu có
        if (appointment.getSchedule() != null && appointment.getSchedule().getId() != null) {
            logger.info("Schedule found for appointment ID: {}, schedule_id: {}", request.getAppointmentId(), appointment.getSchedule().getId());
        } else {
            logger.info("No schedule associated with appointment ID: {}", request.getAppointmentId());
        }

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

        logger.info("Doctor username: {}, user_id: {}", doctorUser.getUsername(), doctorUser.getId());
        if (!doctorUser.getUsername().equals(username)) {
            logger.warn("User {} is not authorized to create prescription for appointment ID: {}. Expected doctor username: {}",
                    username, request.getAppointmentId(), doctorUser.getUsername());
            throw new IllegalArgumentException("Bạn không có quyền kê đơn cho lịch hẹn này. Yêu cầu username bác sĩ: " + doctorUser.getUsername());
        }

        // Tìm bệnh
        Disease disease = diseaseRepository.findById(request.getDiseaseId())
                .orElseThrow(() -> {
                    logger.error("Disease not found with ID: {}", request.getDiseaseId());
                    return new IllegalArgumentException("Bệnh không tồn tại với ID: " + request.getDiseaseId());
                });
        logger.info("Found disease with ID: {}, name: {}", disease.getId(), disease.getNameVi());

        // Lấy thông tin bệnh nhân từ Appointment
        User patient = appointment.getUser();
        if (patient == null) {
            logger.error("Patient not found for appointment ID: {}", request.getAppointmentId());
            throw new IllegalArgumentException("Không tìm thấy thông tin bệnh nhân cho lịch hẹn này");
        }

        // Tính tuổi bệnh nhân
        Integer patientAge = null;
        if (patient.getDateOfBirth() != null) {
            patientAge = LocalDate.now().getYear() - patient.getDateOfBirth().getYear();
        } else {
            logger.warn("Patient date of birth is missing for user ID: {}. Using default age: 0", patient.getId());
            patientAge = 0;
        }

        // Gán tên bệnh nhân bằng username
        String patientName = patient.getUsername() != null ? patient.getUsername() : "Không xác định";
        logger.info("Patient username for user ID: {} is: {}", patient.getId(), patientName);

        // Tạo đơn thuốc
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setNote(request.getMedicines().stream()
                .map(med -> "Thuốc: " + med.getMedicineId() + ", " + med.getNote())
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : "; ") + b));
        prescription.setStatus("ACTIVE");

        // Tính tổng chi phí
        double totalCost = 0.0;
        List<PrescriptionResponseDTO.MedicineDetail> responseMedicines = new ArrayList<>();
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();

        // Xử lý từng thuốc
        for (PrescriptionRequestDTO.MedicineDetail med : request.getMedicines()) {
            Medicine medicine = medicineRepository.findById(med.getMedicineId())
                    .orElseThrow(() -> {
                        logger.error("Medicine not found with ID: {}", med.getMedicineId());
                        return new IllegalArgumentException("Thuốc không tồn tại với ID: " + med.getMedicineId());
                    });
            logger.info("Found medicine with ID: {}, name: {}, stock_quantity: {}", medicine.getId(), medicine.getName(), medicine.getStockQuantity());

            // Kiểm tra số lượng tồn kho
            if (medicine.getStockQuantity() == null || medicine.getStockQuantity() < med.getQuantity()) {
                logger.error("Insufficient stock for medicine: {}, requested: {}, available: {}", medicine.getName(), med.getQuantity(), medicine.getStockQuantity());
                throw new IllegalArgumentException("Không đủ tồn kho cho thuốc: " + medicine.getName());
            }

            // Tính chi phí cho thuốc
            double medicineCost = medicine.getPrice() != null ? medicine.getPrice() * med.getQuantity() : 0.0;
            totalCost += medicineCost;

            // Cập nhật số lượng tồn kho
            medicine.setStockQuantity(medicine.getStockQuantity() - med.getQuantity());
            medicineRepository.save(medicine);

            // Tạo chi tiết đơn thuốc
            PrescriptionDetail detail = new PrescriptionDetail();
            detail.setPrescription(prescription);
            detail.setMedicine(medicine);
            detail.setQuantity(med.getQuantity());
            detail.setDosage(med.getDosage());
            detail.setDuration(med.getDuration());
            detail.setNote(med.getNote());
            prescriptionDetails.add(detail);

            // Thêm vào response
            PrescriptionResponseDTO.MedicineDetail responseMed = new PrescriptionResponseDTO.MedicineDetail();
            responseMed.setMedicineName(medicine.getName());
            responseMed.setUnit(medicine.getUnit());
            responseMed.setQuantity(med.getQuantity());
            responseMed.setDosage(med.getDosage());
            responseMed.setDuration(med.getDuration());
            responseMed.setNote(med.getNote());
            responseMedicines.add(responseMed);
        }

        // Gán tổng chi phí
        prescription.setTotalCost(totalCost);

        // Log id trước khi lưu
        logger.info("Attempting to save prescription with id: {} (will be generated by database)", prescription.getId());

        // Lưu đơn thuốc
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        logger.info("Prescription saved with ID: {}", savedPrescription.getId());

        // Lưu chi tiết đơn thuốc
        for (PrescriptionDetail detail : prescriptionDetails) {
            detail.setPrescription(savedPrescription);
            prescriptionDetailRepository.save(detail);
            logger.info("Prescription detail saved for prescription ID: {}, medicine ID: {}", savedPrescription.getId(), detail.getMedicine().getId());
        }

        // Định dạng prescriptionDate từ giờ hiện tại
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = now.format(formatter);
        logger.info("Prescription date set to current time: {}", formattedDate);

        // Tạo response
        PrescriptionResponseDTO response = new PrescriptionResponseDTO();
        response.setId(savedPrescription.getId());
        response.setPatientName(patientName);
        response.setPatientAge(patientAge);
        response.setPatientGender(patient.getGender());
        response.setDiseaseNameVi(disease.getNameVi());
        response.setMedicines(responseMedicines);
        response.setPrescriptionDate(formattedDate);

        logger.info("Prescription created successfully for patient: {}", patientName);
        return response;
    }

    @Transactional(readOnly = true)
    public PrescriptionDTO getPrescriptionDetailsByAppointmentId(Long appointmentId) {
        Prescription prescription = prescriptionRepository.findActiveByAppointmentIdWithDoctor(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found for appointment id: " + appointmentId));

        if (prescription.getDoctor() == null) {
            throw new IllegalArgumentException("Doctor not found for this prescription");
        }

        List<PrescriptionDetailDTO> details = prescriptionDetailRepository.findByPrescriptionId(prescription.getId())
                .stream()
                .map(detail -> new PrescriptionDetailDTO(
                        detail.getMedicine().getId(),
                        detail.getMedicine().getName(),
                        detail.getDosage(),
                        detail.getDuration(),
                        detail.getNote()
                ))
                .collect(Collectors.toList());

        return new PrescriptionDTO(
                prescription.getDoctor().getName(),
                prescription.getPatient().getUsername(),
                prescription.getTotalCost(),
                prescription.getNote(),
                details
        );
    }


}