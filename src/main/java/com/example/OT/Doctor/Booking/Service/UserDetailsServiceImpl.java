package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.UserEditDTO;
import com.example.OT.Doctor.Booking.DTO.UserInfoDTO;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Giữ lại để hỗ trợ các chức năng cũ (nếu cần)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with userId: " + userId));
        return UserDetailsImpl.build(user);
    }

    public UserInfoDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return new UserInfoDTO(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getAddress(),
                user.getGender() != null ? user.getGender().name() : null
        );
    }

    @Transactional
    public UserInfoDTO editUser(Long userId, UserEditDTO userEditDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        // Kiểm tra email nếu thay đổi
        if (userEditDTO.getEmail() != null && !userEditDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userEditDTO.getEmail())) {
                throw new RuntimeException("Email đã được sử dụng");
            }
            user.setEmail(userEditDTO.getEmail());
        }

        // Cập nhật username (cho phép trùng lặp)
        if (userEditDTO.getUsername() != null) {
            user.setUsername(userEditDTO.getUsername());
        }

        // Cập nhật phoneNumber
        if (userEditDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userEditDTO.getPhoneNumber());
        }

        // Cập nhật address
        if (userEditDTO.getAddress() != null) {
            user.setAddress(userEditDTO.getAddress());
        }

        // Cập nhật gender
        if (userEditDTO.getGender() != null) {
            user.setGender(userEditDTO.getGender());
        }

        // Lưu thay đổi
        userRepository.save(user);

        // Trả về thông tin người dùng đã cập nhật
        return new UserInfoDTO(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getAddress(),
                user.getGender() != null ? user.getGender().name() : null
        );
    }
}