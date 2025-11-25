package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Dto.UserResponseDTO;
import com.example.email_notifier_backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public UserResponseDTO getProfile(Authentication auth) {
        return userService.getProfile(auth.getName());
    }

}
