package com.example.email_notifier_backend.Security;

import lombok.Data;

@Data
class LoginRequest {
    private String email;
    private String password;
}
