package com.example.email_notifier_backend.Dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateEventDTO {
    private String title;
    private String description;
    private LocalDate eventDate;   // "2025-11-21"
    private LocalTime eventTime;   // "18:48"
    private String recipients;
}
