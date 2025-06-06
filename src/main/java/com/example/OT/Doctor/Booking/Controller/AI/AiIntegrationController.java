package com.example.OT.Doctor.Booking.Controller.AI;

import com.example.OT.Doctor.Booking.DTO.AI.PredictionResponse;
import com.example.OT.Doctor.Booking.DTO.AI.SymptomsRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class AiIntegrationController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/predict-disease")
    public ResponseEntity<?> predictDisease(@RequestBody SymptomsRequest request) {
        String aiUrl = "http://localhost:8000/predict"; // hoặc IP máy chủ nếu deploy riêng

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SymptomsRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<PredictionResponse> response = restTemplate.exchange(
                    aiUrl, HttpMethod.POST, entity, PredictionResponse.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể kết nối đến AI server: " + e.getMessage());
        }
    }
}

