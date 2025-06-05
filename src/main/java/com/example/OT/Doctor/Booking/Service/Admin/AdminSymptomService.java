package com.example.OT.Doctor.Booking.Service.Admin;

import com.example.OT.Doctor.Booking.DTO.Admin.SymptomCreateRequestDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.SymptomResponseDTO;
import com.example.OT.Doctor.Booking.DTO.Admin.SymptomUpdateRequestDTO;
import com.example.OT.Doctor.Booking.Entity.Symptom;
import com.example.OT.Doctor.Booking.Repository.SymptomRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminSymptomService {

    private static final Logger logger = LoggerFactory.getLogger(AdminSymptomService.class);

    @Autowired
    private SymptomRepository symptomRepository;

    @Transactional
    public SymptomResponseDTO createSymptom(SymptomCreateRequestDTO request) {
        logger.info("Creating symptom with nameEn: {} and nameVi: {}", request.getNameEn(), request.getNameVi());

        Symptom symptom = new Symptom();
        symptom.setNameEn(request.getNameEn());
        symptom.setNameVi(request.getNameVi());
        symptom.setDescription(request.getDescription());

        try {
            Symptom savedSymptom = symptomRepository.save(symptom);
            logger.info("Saved symptom with ID: {}", savedSymptom.getId());
            return mapToSymptomResponseDTO(savedSymptom);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to create symptom due to duplicate key: {}", e.getMessage());
            throw new IllegalArgumentException("Không thể tạo triệu chứng: ID đã tồn tại. Vui lòng kiểm tra sequence trong database.");
        }
    }

    @Transactional
    public SymptomResponseDTO updateSymptom(Long id, SymptomUpdateRequestDTO request) {
        logger.info("Updating symptom with ID: {}", id);

        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Symptom not found with ID: {}", id);
                    return new IllegalArgumentException("Triệu chứng không tồn tại với ID: " + id);
                });

        if (request.getNameEn() != null) {
            symptom.setNameEn(request.getNameEn());
        }
        if (request.getNameVi() != null) {
            symptom.setNameVi(request.getNameVi());
        }
        if (request.getDescription() != null) {
            symptom.setDescription(request.getDescription());
        }

        Symptom updatedSymptom = symptomRepository.save(symptom);
        logger.info("Updated symptom with ID: {}", updatedSymptom.getId());

        return mapToSymptomResponseDTO(updatedSymptom);
    }

    @Transactional
    public void deleteSymptom(Long id) {
        logger.info("Deleting symptom with ID: {}", id);

        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Symptom not found with ID: {}", id);
                    return new IllegalArgumentException("Triệu chứng không tồn tại với ID: " + id);
                });
        symptomRepository.delete(symptom);
        logger.info("Deleted symptom with ID: {}", id);
    }

    public List<SymptomResponseDTO> getAllSymptoms() {
        logger.info("Fetching all symptoms");

        List<Symptom> symptoms = symptomRepository.findAll();
        return symptoms.stream()
                .map(this::mapToSymptomResponseDTO)
                .collect(Collectors.toList());
    }

    private SymptomResponseDTO mapToSymptomResponseDTO(Symptom symptom) {
        SymptomResponseDTO response = new SymptomResponseDTO();
        response.setId(symptom.getId());
        response.setNameEn(symptom.getNameEn());
        response.setNameVi(symptom.getNameVi());
        response.setDescription(symptom.getDescription());
        return response;
    }
}