package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.UserDTO;

public interface UserService {
    UserDTO register(UserDTO dto);

    UserDTO login(String email, String password);

    String generatePasswordResetToken(String email);

    String resetPassword(String token, String newPassword);

    UserDTO getById(Long id);

    UserDTO update(Long id, UserDTO dto);
    //UserDTO create(UserDTO dto);
}
