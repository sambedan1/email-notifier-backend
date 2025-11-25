package com.example.email_notifier_backend.Dto;
import lombok.*;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuthResponseDTO {
    private String token;     // JWT you send on login/signup
    private Long userId;
    private String name;
    private String email;
    private String role;

}
