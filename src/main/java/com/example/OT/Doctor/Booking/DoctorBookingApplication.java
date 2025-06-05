package com.example.OT.Doctor.Booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.OT.Doctor.Booking.Repository")
@EntityScan(basePackages = "com.example.OT.Doctor.Booking.Entity")
@SpringBootApplication
public class DoctorBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorBookingApplication.class, args);
	}

}
