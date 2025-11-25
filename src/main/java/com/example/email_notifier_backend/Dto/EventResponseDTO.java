package com.example.email_notifier_backend.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private boolean approved;
    private Set<String> recipientEmails;
    private Long userId;
}
