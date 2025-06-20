package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.UserEditDTO;
import com.example.OT.Doctor.Booking.DTO.UserInfoDTO;
import com.example.OT.Doctor.Booking.Service.UserDetailsServiceImpl;
import com.example.OT.Doctor.Booking.Service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            UserInfoDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editUser(@Valid @RequestBody UserEditDTO userEditDTO) {
        try {
            // Lấy UserDetails từ SecurityContext
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getId(); // Lấy userId từ UserDetailsImpl
            UserInfoDTO updatedUser = userService.editUser(userId, userEditDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}