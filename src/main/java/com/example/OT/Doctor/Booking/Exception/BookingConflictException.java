// File: src/main/java/com/example/OT/Doctor/Booking/Exception/BookingConflictException.java
package com.example.OT.Doctor.Booking.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookingConflictException extends RuntimeException {
    public BookingConflictException(String message) {
        super(message);
    }
}