package com.example.email_notifier_backend.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventResponseDTO {
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String recipients;
    private boolean approved;
}
