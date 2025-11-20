package com.example.email_notifier_backend.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDTO {
    private Long id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private boolean approved;
    private Long userId;
}
