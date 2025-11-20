package com.example.email_notifier_backend.Dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;   // will not return it to frontend
    private String role;
}
