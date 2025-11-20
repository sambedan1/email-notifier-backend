package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO dto) {
        return userService.login(dto.getEmail(), dto.getPassword());
    }

    @PostMapping("/password-reset/request")
    public String generateReset(@RequestBody UserDTO dto) {
        return userService.generatePasswordResetToken(dto.getEmail());
    }

    @PostMapping("/password-reset/confirm")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword) {
        return userService.resetPassword(token, newPassword);
    }
}
