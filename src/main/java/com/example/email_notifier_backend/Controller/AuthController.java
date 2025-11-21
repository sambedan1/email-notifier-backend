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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    @PostMapping("/signup")
    public AuthResponseDTO signup(@RequestBody AuthRequestDTO dto){
        if(userRepo.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User u = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepo.save(u);
        String token = jwt.generateToken(u.getEmail());
        return new AuthResponseDTO(token,u.getName(),u.getEmail());
    }

    @PostMapping("/signin")
    public  String signin(@RequestBody AuthRequestDTO req) {
        var user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid creds"));
        if(!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid creds");
        String token = jwt.generateToken(user.getEmail());
        return "Sign-in successful";
    }
}
