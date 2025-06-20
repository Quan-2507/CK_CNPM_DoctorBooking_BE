package com.example.OT.Doctor.Booking.Service;

import com.example.OT.Doctor.Booking.DTO.UserEditDTO;
import com.example.OT.Doctor.Booking.DTO.UserInfoDTO;
import com.example.OT.Doctor.Booking.Entity.User;
import com.example.OT.Doctor.Booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Dùng khi bạn xác thực qua username (vẫn giữ để dùng ở các chỗ khác nếu có)
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    // Dùng khi xác thực qua ID (từ JWT token)
    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with ID: " + id));
        return UserDetailsImpl.build(user);
    }

    // Trả về thông tin người dùng (không bao gồm password)
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

    // Chỉnh sửa thông tin user qua username
    @Transactional
    public UserInfoDTO editUser(String username, UserEditDTO userEditDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Kiểm tra email nếu thay đổi
        if (userEditDTO.getEmail() != null && !userEditDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userEditDTO.getEmail())) {
                throw new RuntimeException("Email đã được sử dụng");
            }
            user.setEmail(userEditDTO.getEmail());
        }

        // Cập nhật username nếu có
        if (userEditDTO.getUsername() != null) {
            user.setUsername(userEditDTO.getUsername());
        }

        // Cập nhật số điện thoại
        if (userEditDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userEditDTO.getPhoneNumber());
        }

        // Cập nhật địa chỉ
        if (userEditDTO.getAddress() != null) {
            user.setAddress(userEditDTO.getAddress());
        }

        // Cập nhật giới tính
        if (userEditDTO.getGender() != null) {
            user.setGender(userEditDTO.getGender());
        }

        // Lưu thay đổi
        userRepository.save(user);

        // Trả về thông tin đã cập nhật
        return new UserInfoDTO(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getAddress(),
                user.getGender() != null ? user.getGender().name() : null
        );
    }
}
