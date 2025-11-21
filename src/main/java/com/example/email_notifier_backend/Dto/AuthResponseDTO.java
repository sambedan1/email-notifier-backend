package com.example.email_notifier_backend.Dto;
import lombok.*;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String name;
    private String email;

}
