package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.AuthRequestDTO;
import com.example.email_notifier_backend.Dto.AuthResponseDTO;
import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Entity.User;
import com.example.email_notifier_backend.Repository.UserRepository;
import com.example.email_notifier_backend.Security.JwtUtil;
import com.example.email_notifier_backend.Service.UserService;
import com.example.email_notifier_backend.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO dto) {
        AuthResponseDTO response = userService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        AuthResponseDTO response = userService.login(dto);
        return ResponseEntity.ok(response);
    }
}
