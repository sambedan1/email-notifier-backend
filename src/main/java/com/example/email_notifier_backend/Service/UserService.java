package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.AuthRequestDTO;
import com.example.email_notifier_backend.Dto.AuthResponseDTO;
import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Dto.UserResponseDTO;

public interface UserService {

    //UserDTO create(UserDTO dto);
    AuthResponseDTO register(AuthRequestDTO dto);
    AuthResponseDTO login(AuthRequestDTO dto);
    UserResponseDTO getProfile(String email);
    Long getUserIdFromEmail(String email);
}
