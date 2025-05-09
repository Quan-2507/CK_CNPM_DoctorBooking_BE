package com.example.OT.Doctor.Booking.Controller;

import com.example.OT.Doctor.Booking.DTO.UserEditDTO;
import com.example.OT.Doctor.Booking.DTO.UserInfoDTO;
import com.example.OT.Doctor.Booking.Service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> editUser(@RequestBody UserEditDTO userEditDTO) {
        try {
            // Lấy username của người dùng đã xác thực từ SecurityContext
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserInfoDTO updatedUser = userService.editUser(username, userEditDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
